package br.unitins.tp2.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.nio.file.Paths;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unitins.tp2.model.Arquivo;
import br.unitins.tp2.model.Plano;
import br.unitins.tp2.repository.ArquivoRepository;
import br.unitins.tp2.repository.PlanoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PlanoFileServiceImpl implements FileService {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final long MIN_FILE_SIZE = 1L * 1024;

    @Inject
    PlanoRepository planoRepository;

    @Inject
    ArquivoRepository arquivoRepository;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "seaweedfs.master.url", defaultValue = "http://localhost:9333")
    String masterUrl;

    @ConfigProperty(name = "seaweedfs.request-timeout-ms", defaultValue = "10000")
    int timeoutMs;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    @Transactional
    public void salvar(Long id, FileUpload file) throws IOException {
        Plano plano = planoRepository.findById(id);
        if (plano == null) {
            throw new NotFoundException("Plano não encontrado.");
        }

        validarTamanho(file);
        validarExtensaoArquivo(file);

        String originalName = Paths.get(file.fileName()).getFileName().toString();
        String mimeType = resolveMimeType(originalName, file.contentType());
        byte[] fileBytes = Files.readAllBytes(file.uploadedFile());

        String fid = uploadParaSeaweed(originalName, mimeType, fileBytes);
        Arquivo arquivo = buildArquivoEntity(originalName, mimeType, file.size(), sha256Hex(file.uploadedFile()), fid);
        arquivoRepository.persist(arquivo);
        plano.addArquivo(arquivo);
    }

    @Transactional
    public void salvarLocal(Long id, String originalName, String mimeType, byte[] fileBytes) {
        Plano plano = planoRepository.findById(id);
        if (plano == null) {
            throw new NotFoundException("Plano não encontrado.");
        }
        if (!plano.getArquivos().isEmpty()) {
            return;
        }

        validarTamanho(fileBytes);
        validarExtensaoNome(originalName);

        String safeName = Paths.get(originalName).getFileName().toString();
        String resolvedMimeType = resolveMimeType(safeName, mimeType);
        String fid = uploadParaSeaweed(safeName, resolvedMimeType, fileBytes);
        Arquivo arquivo = buildArquivoEntity(safeName, resolvedMimeType, (long) fileBytes.length, sha256Hex(fileBytes), fid);
        arquivoRepository.persist(arquivo);
        plano.addArquivo(arquivo);
    }

    private Arquivo buildArquivoEntity(String originalName, String mimeType, Long tamanhoBytes, String sha256, String fid) {
        Arquivo arquivo = new Arquivo();
        arquivo.setFid(fid);
        arquivo.setNomeOriginal(originalName);
        arquivo.setMimeType(mimeType);
        arquivo.setTamanhoBytes(tamanhoBytes);
        arquivo.setSha256(sha256);
        return arquivo;
    }

    private String uploadParaSeaweed(String fileName, String contentType, byte[] fileBytes) {
        // Normaliza o nome apenas para envio; o metadado original fica salvo no banco.
        String extension = getExtension(fileName);
        String normalizedName = UUID.randomUUID() + (extension == null ? "" : "." + extension.toLowerCase(Locale.ROOT));

        // 1) Solicita ao master um FID e o endereço do volume responsável pelo upload.
        JsonNode assignJson = requestJson("GET", masterUrl + "/dir/assign", null, null);
        String fid = text(assignJson, "fid");
        String volumeUrl = text(assignJson, "publicUrl");
        if (volumeUrl == null) {
            volumeUrl = text(assignJson, "url");
        }

        if (fid == null || volumeUrl == null) {
            throw new WebApplicationException("Resposta inválida do SeaweedFS ao alocar arquivo.", Response.Status.BAD_GATEWAY);
        }

        // 2) Monta multipart manualmente para enviar o conteúdo ao volume indicado.
        String boundary = "----QuarkusSeaweedBoundary" + UUID.randomUUID();
        byte[] body = buildMultipartBody(boundary, normalizedName, contentType, fileBytes);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + volumeUrl + "/" + fid))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        // 3) Executa o upload e converte interrupção de thread para IOException controlada.
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException("Upload interrompido.", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao enviar imagem para o SeaweedFS.", e, Response.Status.BAD_GATEWAY);
        }

        // 4) Qualquer retorno fora de 2xx vira erro de gateway entre API e storage.
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao enviar imagem para o SeaweedFS.", Response.Status.BAD_GATEWAY);
        }

        // O FID é o identificador persistido no banco para futuras operações (download/delete).
        return fid;
    }

    @Override
    public ArquivoDownload download(String fid) {
        if (fid == null || fid.isBlank()) {
            throw new WebApplicationException("Identificador de imagem inválido.", Response.Status.BAD_REQUEST);
        }

        Arquivo meta = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException("Imagem não encontrada no banco de dados.", Response.Status.NOT_FOUND));

        String volumeId = extractVolumeId(fid);
        JsonNode lookup = requestJson("GET", masterUrl + "/dir/lookup?volumeId=" + volumeId, null, null);
        JsonNode locations = lookup.get("locations");

        if (locations == null || !locations.isArray() || locations.isEmpty()) {
            throw new WebApplicationException("Imagem não encontrada no SeaweedFS.", Response.Status.NOT_FOUND);
        }

        String publicUrl = text(locations.get(0), "publicUrl");
        if (publicUrl == null) {
            publicUrl = text(locations.get(0), "url");
        }

        if (publicUrl == null) {
            throw new WebApplicationException("Lookup de imagem sem endereço válido.", Response.Status.BAD_GATEWAY);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + publicUrl + "/" + fid))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .GET()
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 404) {
                throw new WebApplicationException("Imagem não encontrada.", Response.Status.NOT_FOUND);
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WebApplicationException("Falha ao baixar imagem do SeaweedFS.", Response.Status.BAD_GATEWAY);
            }
            return new ArquivoDownload(response.body(), meta.getMimeType(), meta.getNomeOriginal());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException("Download interrompido.", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao baixar imagem.", e, Response.Status.BAD_GATEWAY);
        }
    }

    @Override
    @Transactional
    public void remover(String fid) {
        if (fid == null || fid.isBlank()) {
            throw new WebApplicationException("Identificador de imagem inválido.", Response.Status.BAD_REQUEST);
        }

        Arquivo arquivo = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException("Imagem não encontrada no banco de dados.", Response.Status.NOT_FOUND));

        deletarNoSeaweedBestEffort(fid);
        planoRepository.findByArquivoId(arquivo.getId()).ifPresent(plano -> plano.removeArquivo(arquivo));
        arquivoRepository.delete(arquivo);
    }

    private void deletarNoSeaweedBestEffort(String fid) {
        try {
            String volumeId = extractVolumeId(fid);
            JsonNode lookup = requestJson("GET", masterUrl + "/dir/lookup?volumeId=" + volumeId, null, null);
            JsonNode locations = lookup.get("locations");
            if (locations == null || !locations.isArray() || locations.isEmpty()) {
                return;
            }

            String publicUrl = text(locations.get(0), "publicUrl");
            if (publicUrl == null) {
                publicUrl = text(locations.get(0), "url");
            }
            if (publicUrl == null) {
                return;
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + publicUrl + "/" + fid))
                    .timeout(java.time.Duration.ofMillis(timeoutMs))
                    .DELETE()
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception ignored) {
            // exclusão remota é best-effort para não impedir a remoção do metadado no banco
        }
    }

    private JsonNode requestJson(String method, String url, String contentType, byte[] body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofMillis(timeoutMs));

        if (contentType != null) {
            builder.header("Content-Type", contentType);
        }

        if ("POST".equalsIgnoreCase(method)) {
            builder.POST(HttpRequest.BodyPublishers.ofByteArray(body == null ? new byte[0] : body));
        } else if ("DELETE".equalsIgnoreCase(method)) {
            builder.DELETE();
        } else {
            builder.GET();
        }

        try {
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WebApplicationException("Erro de comunicação com SeaweedFS.", Response.Status.BAD_GATEWAY);
            }
            return objectMapper.readTree(response.body());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException("Operação com SeaweedFS interrompida.", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao processar resposta do SeaweedFS.", e, Response.Status.BAD_GATEWAY);
        }
    }

    private byte[] buildMultipartBody(String boundary, String fileName, String contentType, byte[] bytes) {
        String safeContentType = (contentType == null || contentType.isBlank()) ? "application/octet-stream" : contentType;

        byte[] prefix = (
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n" +
                "Content-Type: " + safeContentType + "\r\n\r\n")
                .getBytes(StandardCharsets.UTF_8);

        byte[] suffix = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);
        byte[] body = new byte[prefix.length + bytes.length + suffix.length];

        System.arraycopy(prefix, 0, body, 0, prefix.length);
        System.arraycopy(bytes, 0, body, prefix.length, bytes.length);
        System.arraycopy(suffix, 0, body, prefix.length + bytes.length, suffix.length);

        return body;
    }

    private void validarTamanho(FileUpload file) {
        if (file == null || file.uploadedFile() == null) {
            throw new WebApplicationException("Arquivo de imagem não informado.", Response.Status.BAD_REQUEST);
        }

        long size = file.size();
        if (size <= 0) {
            throw new WebApplicationException("Arquivo vazio.", Response.Status.BAD_REQUEST);
        }
        if (size < MIN_FILE_SIZE) {
            throw new WebApplicationException("Arquivo muito pequeno para ser considerado imagem válida.", Response.Status.BAD_REQUEST);
        }
        if (size > MAX_FILE_SIZE) {
            throw new WebApplicationException("Arquivo muito grande. Máximo permitido: " + MAX_FILE_SIZE + " bytes.", Response.Status.BAD_REQUEST);
        }
    }

    private void validarExtensaoArquivo(FileUpload file) {
        String ext = getExtension(file.fileName());
        validarExtensao(ext);
    }

    private void validarExtensaoNome(String fileName) {
        String ext = getExtension(fileName);
        validarExtensao(ext);
    }

    private void validarExtensao(String ext) {
        if (ext == null || !ALLOWED_EXTENSIONS.contains(ext.toLowerCase(Locale.ROOT))) {
            throw new WebApplicationException("Extensão de arquivo não suportada.", Response.Status.BAD_REQUEST);
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String onlyName = Paths.get(fileName).getFileName().toString();
        int idx = onlyName.lastIndexOf('.');
        if (idx == -1 || idx == onlyName.length() - 1) {
            return null;
        }
        return onlyName.substring(idx + 1);
    }

    private String extractVolumeId(String fid) {
        int comma = fid.indexOf(',');
        if (comma <= 0) {
            throw new WebApplicationException("FID inválido para lookup no SeaweedFS.", Response.Status.BAD_REQUEST);
        }
        return fid.substring(0, comma);
    }

    private String sha256Hex(java.nio.file.Path uploadedPath) throws IOException {
        try (InputStream is = Files.newInputStream(uploadedPath)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int n;
            while ((n = is.read(buffer)) > 0) {
                digest.update(buffer, 0, n);
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível na JVM.", e);
        }
    }

    private String sha256Hex(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível na JVM.", e);
        }
    }

    private void validarTamanho(byte[] bytes) {
        if (bytes == null) {
            throw new WebApplicationException("Arquivo de imagem não informado.", Response.Status.BAD_REQUEST);
        }
        long size = bytes.length;
        if (size <= 0) {
            throw new WebApplicationException("Arquivo vazio.", Response.Status.BAD_REQUEST);
        }
        if (size < MIN_FILE_SIZE) {
            throw new WebApplicationException("Arquivo muito pequeno para ser considerado imagem válida.", Response.Status.BAD_REQUEST);
        }
        if (size > MAX_FILE_SIZE) {
            throw new WebApplicationException("Arquivo muito grande. Máximo permitido: " + MAX_FILE_SIZE + " bytes.", Response.Status.BAD_REQUEST);
        }
    }

    private String resolveMimeType(String originalName, String mimeType) {
        if (mimeType == null || mimeType.isBlank()) {
            return guessMimeTypeByExtension(getExtension(originalName));
        }
        return mimeType;
    }

    private String guessMimeTypeByExtension(String ext) {
        if (ext == null) {
            return "application/octet-stream";
        }
        return switch (ext.toLowerCase(Locale.ROOT)) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value == null || value.isNull() ? null : value.asText();
    }
}
