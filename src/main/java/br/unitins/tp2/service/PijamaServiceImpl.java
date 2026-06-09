package br.unitins.tp2.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unitins.tp2.dto.PijamaDTO;
import br.unitins.tp2.dto.PijamaResponseDTO;
import br.unitins.tp2.dto.pijama.PijamaVarianteRequestDTO;
import br.unitins.tp2.model.Arquivo;
import br.unitins.tp2.model.Cor;
import br.unitins.tp2.model.Pijama;
import br.unitins.tp2.model.PijamaVariante;
import br.unitins.tp2.model.SexoPijama;
import br.unitins.tp2.model.Tamanho;
import br.unitins.tp2.repository.ArquivoRepository;
import br.unitins.tp2.repository.CategoriaRepository;
import br.unitins.tp2.repository.CorRepository;
import br.unitins.tp2.repository.EstampaRepository;
import br.unitins.tp2.repository.MarcaRepository;
import br.unitins.tp2.repository.MaterialRepository;
import br.unitins.tp2.repository.PijamaRepository;
import br.unitins.tp2.repository.PijamaVarianteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PijamaServiceImpl implements PijamaService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final long MIN_FILE_SIZE = 1L * 1024;

    @Inject
    PijamaRepository pijamaRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    EstampaRepository estampaRepository;

    @Inject
    CorRepository corRepository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    ArquivoRepository arquivoRepository;

    @Inject
    PijamaVarianteRepository pijamaVarianteRepository;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Validator validator;

    @ConfigProperty(name = "seaweedfs.master.url", defaultValue = "http://localhost:9333")
    String masterUrl;

    @ConfigProperty(name = "seaweedfs.request-timeout-ms", defaultValue = "10000")
    int timeoutMs;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    @Transactional
    public PijamaResponseDTO create(PijamaDTO dto) {
        validar(dto);
        Pijama entity = new Pijama();
        apply(dto, entity);
        pijamaRepository.persist(entity);
        salvarVariantes(entity, dto.variantes());
        return PijamaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public PijamaResponseDTO update(Long id, PijamaDTO dto) {
        validar(dto);
        Pijama entity = pijamaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Pijama não encontrado.");
        apply(dto, entity);
        salvarVariantes(entity, dto.variantes());
        return PijamaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = pijamaRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Pijama não encontrado.");
    }

    @Override
    public PijamaResponseDTO findById(Long id) {
        Pijama entity = pijamaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Pijama não encontrado.");
        return PijamaResponseDTO.valueOf(entity);
    }

    @Override
    public List<PijamaResponseDTO> findAll(int page, int pageSize) {
        return pijamaRepository.findAll().page(page, pageSize).list()
                .stream().map(PijamaResponseDTO::valueOf).toList();
    }

    @Override
    public List<PijamaResponseDTO> findByNome(String nome, int page, int pageSize) {
        return pijamaRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(PijamaResponseDTO::valueOf).toList();
    }

    @Override
    public List<PijamaResponseDTO> findByCategoria(Long idCategoria, int page, int pageSize) {
        return pijamaRepository.findByCategoria(idCategoria).page(page, pageSize).list()
                .stream().map(PijamaResponseDTO::valueOf).toList();
    }

    @Override
    public List<PijamaResponseDTO> findByMarca(Long idMarca, int page, int pageSize) {
        return pijamaRepository.findByMarca(idMarca).page(page, pageSize).list()
                .stream().map(PijamaResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return pijamaRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return pijamaRepository.countByNome(nome);
    }

    @Override
    @Transactional
    public void adicionarImagem(Long id, FileUpload file) throws IOException {
        Pijama pijama = pijamaRepository.findById(id);
        if (pijama == null) throw new NotFoundException("Pijama não encontrado.");

        validarTamanho(file);
        validarExtensao(file.fileName());

        String originalName = Paths.get(file.fileName()).getFileName().toString();
        String mimeType = resolveMimeType(originalName, file.contentType());
        byte[] fileBytes = Files.readAllBytes(file.uploadedFile());

        String fid = uploadParaSeaweed(originalName, mimeType, fileBytes);

        Arquivo arquivo = new Arquivo();
        arquivo.setFid(fid);
        arquivo.setNomeOriginal(originalName);
        arquivo.setMimeType(mimeType);
        arquivo.setTamanhoBytes(file.size());
        arquivo.setSha256(sha256Hex(file.uploadedFile()));
        arquivoRepository.persist(arquivo);

        pijama.addImagem(arquivo);
    }

    @Override
    public ArquivoDownload downloadImagem(String fid) {
        if (fid == null || fid.isBlank())
            throw new WebApplicationException("Identificador de imagem inválido.", Response.Status.BAD_REQUEST);

        Arquivo meta = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException("Imagem não encontrada.", Response.Status.NOT_FOUND));

        String volumeId = extractVolumeId(fid);
        JsonNode lookup = requestJson("GET", masterUrl + "/dir/lookup?volumeId=" + volumeId, null, null);
        JsonNode locations = lookup.get("locations");
        if (locations == null || !locations.isArray() || locations.isEmpty())
            throw new WebApplicationException("Imagem não encontrada no SeaweedFS.", Response.Status.NOT_FOUND);

        String publicUrl = text(locations.get(0), "publicUrl");
        if (publicUrl == null) publicUrl = text(locations.get(0), "url");
        if (publicUrl == null)
            throw new WebApplicationException("Lookup sem endereço válido.", Response.Status.BAD_GATEWAY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + publicUrl + "/" + fid))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .GET().build();

        try {
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 404)
                throw new WebApplicationException("Imagem não encontrada.", Response.Status.NOT_FOUND);
            if (response.statusCode() < 200 || response.statusCode() >= 300)
                throw new WebApplicationException("Falha ao baixar imagem do SeaweedFS.", Response.Status.BAD_GATEWAY);
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
    public void removerImagem(String fid) {
        if (fid == null || fid.isBlank())
            throw new WebApplicationException("Identificador de imagem inválido.", Response.Status.BAD_REQUEST);

        Arquivo arquivo = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException("Imagem não encontrada.", Response.Status.NOT_FOUND));

        deletarNoSeaweedBestEffort(fid);
        pijamaRepository.findByArquivoId(arquivo.getId()).ifPresent(p -> p.removeImagem(arquivo));
        arquivoRepository.delete(arquivo);
    }

    private void apply(PijamaDTO dto, Pijama entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setDescricao(safeTrim(dto.descricao()));
        entity.setPreco(dto.preco());
        entity.setModelo(safeTrim(dto.modelo()));
        entity.setAtivo(dto.ativo());
        entity.setSexo(SexoPijama.valueOf(dto.idSexo()));
        entity.setCategoria(categoriaRepository.findById(dto.idCategoria()));
        entity.setMarca(marcaRepository.findById(dto.idMarca()));
        entity.setEstampa(dto.idEstampa() != null ? estampaRepository.findById(dto.idEstampa()) : null);

        entity.getMateriais().clear();
        if (dto.idsMateriais() != null) {
            dto.idsMateriais().forEach(mid -> {
                var mat = materialRepository.findById(mid);
                if (mat != null) entity.getMateriais().add(mat);
            });
        }
    }

    private void salvarVariantes(Pijama pijama, List<PijamaVarianteRequestDTO> variantesDTO) {
        pijama.getVariantes().clear();
        if (variantesDTO == null || variantesDTO.isEmpty()) return;

        for (PijamaVarianteRequestDTO dto : variantesDTO) {
            Tamanho tamanho;
            try {
                tamanho = Tamanho.valueOf(dto.idTamanho());
            } catch (IllegalArgumentException e) {
                throw new WebApplicationException(
                    Response.status(400)
                        .entity(Map.of("message", "Tamanho inválido: " + dto.idTamanho()))
                        .build());
            }

            PijamaVariante variante = new PijamaVariante();
            variante.setPijama(pijama);
            variante.setTamanho(tamanho);
            variante.setEstoque(dto.estoque());

            if (dto.idCor() != null) {
                Cor cor = corRepository.findById(dto.idCor());
                if (cor == null) throw new WebApplicationException(
                    Response.status(400)
                        .entity(Map.of("message", "Cor não encontrada: " + dto.idCor()))
                        .build());
                variante.setCor(cor);
            }

            pijama.getVariantes().add(variante);
        }
    }

    private void validar(PijamaDTO dto) {
        Set<ConstraintViolation<PijamaDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    // ── SeaweedFS ────────────────────────────────────────────────────────────

    private String uploadParaSeaweed(String fileName, String contentType, byte[] fileBytes) {
        String extension = getExtension(fileName);
        String normalizedName = UUID.randomUUID() + (extension == null ? "" : "." + extension.toLowerCase(Locale.ROOT));

        JsonNode assignJson = requestJson("GET", masterUrl + "/dir/assign", null, null);
        String fid = text(assignJson, "fid");
        String volumeUrl = text(assignJson, "publicUrl");
        if (volumeUrl == null) volumeUrl = text(assignJson, "url");

        if (fid == null || volumeUrl == null)
            throw new WebApplicationException("Resposta inválida do SeaweedFS.", Response.Status.BAD_GATEWAY);

        String boundary = "----QuarkusSeaweedBoundary" + UUID.randomUUID();
        byte[] body = buildMultipartBody(boundary, normalizedName, contentType, fileBytes);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + volumeUrl + "/" + fid))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300)
                throw new WebApplicationException("Falha ao enviar imagem para o SeaweedFS.", Response.Status.BAD_GATEWAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException("Upload interrompido.", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao enviar imagem para o SeaweedFS.", e, Response.Status.BAD_GATEWAY);
        }
        return fid;
    }

    private void deletarNoSeaweedBestEffort(String fid) {
        try {
            String volumeId = extractVolumeId(fid);
            JsonNode lookup = requestJson("GET", masterUrl + "/dir/lookup?volumeId=" + volumeId, null, null);
            JsonNode locations = lookup.get("locations");
            if (locations == null || !locations.isArray() || locations.isEmpty()) return;

            String publicUrl = text(locations.get(0), "publicUrl");
            if (publicUrl == null) publicUrl = text(locations.get(0), "url");
            if (publicUrl == null) return;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + publicUrl + "/" + fid))
                    .timeout(java.time.Duration.ofMillis(timeoutMs))
                    .DELETE().build();
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception ignored) {}
    }

    private JsonNode requestJson(String method, String url, String contentType, byte[] body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofMillis(timeoutMs));
        if (contentType != null) builder.header("Content-Type", contentType);
        if ("POST".equalsIgnoreCase(method))
            builder.POST(HttpRequest.BodyPublishers.ofByteArray(body == null ? new byte[0] : body));
        else if ("DELETE".equalsIgnoreCase(method)) builder.DELETE();
        else builder.GET();

        try {
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300)
                throw new WebApplicationException("Erro de comunicação com SeaweedFS.", Response.Status.BAD_GATEWAY);
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
        byte[] prefix = ("--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n" +
                "Content-Type: " + safeContentType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
        byte[] suffix = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[prefix.length + bytes.length + suffix.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(bytes, 0, result, prefix.length, bytes.length);
        System.arraycopy(suffix, 0, result, prefix.length + bytes.length, suffix.length);
        return result;
    }

    private void validarTamanho(FileUpload file) {
        if (file == null || file.uploadedFile() == null)
            throw new WebApplicationException("Arquivo não informado.", Response.Status.BAD_REQUEST);
        long size = file.size();
        if (size <= 0 || size < MIN_FILE_SIZE)
            throw new WebApplicationException("Arquivo vazio ou muito pequeno.", Response.Status.BAD_REQUEST);
        if (size > MAX_FILE_SIZE)
            throw new WebApplicationException("Arquivo muito grande. Máximo: 5 MB.", Response.Status.BAD_REQUEST);
    }

    private void validarExtensao(String fileName) {
        String ext = getExtension(fileName);
        if (ext == null || !ALLOWED_EXTENSIONS.contains(ext.toLowerCase(Locale.ROOT)))
            throw new WebApplicationException("Extensão não suportada.", Response.Status.BAD_REQUEST);
    }

    private String getExtension(String fileName) {
        if (fileName == null) return null;
        String name = Paths.get(fileName).getFileName().toString();
        int idx = name.lastIndexOf('.');
        return (idx == -1 || idx == name.length() - 1) ? null : name.substring(idx + 1);
    }

    private String extractVolumeId(String fid) {
        int comma = fid.indexOf(',');
        if (comma <= 0)
            throw new WebApplicationException("FID inválido.", Response.Status.BAD_REQUEST);
        return fid.substring(0, comma);
    }

    private String sha256Hex(java.nio.file.Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int n;
            while ((n = is.read(buffer)) > 0) digest.update(buffer, 0, n);
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível.", e);
        }
    }

    private String resolveMimeType(String originalName, String mimeType) {
        if (mimeType == null || mimeType.isBlank()) {
            String ext = getExtension(originalName);
            if (ext == null) return "application/octet-stream";
            return switch (ext.toLowerCase(Locale.ROOT)) {
                case "png" -> "image/png";
                case "jpg", "jpeg" -> "image/jpeg";
                case "gif" -> "image/gif";
                case "webp" -> "image/webp";
                default -> "application/octet-stream";
            };
        }
        return mimeType;
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value == null || value.isNull() ? null : value.asText();
    }
}
