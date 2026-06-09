package br.unitins.tp2.resource;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unitins.tp2.dto.PijamaDTO;
import br.unitins.tp2.dto.PijamaResponseDTO;
import br.unitins.tp2.dto.arquivo.ReordenarImagemDTO;
import br.unitins.tp2.dto.pijama.PijamaVarianteRequestDTO;
import br.unitins.tp2.service.ArquivoDownload;
import br.unitins.tp2.service.PijamaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;

@Path("/pijamas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PijamaResource {

    private static final int MAX_PAGE_SIZE = 100;

    @Inject
    PijamaService service;

    @Inject
    ObjectMapper objectMapper;

    @GET
    public Response buscarTodos(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        return Response.ok(service.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", service.count())
                .build();
    }

    @GET
    @Path("/{id: \\d+}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        return Response.ok(service.findByNome(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", service.countByNome(nome))
                .build();
    }

    @GET
    @Path("/search/categoria/{id: \\d+}")
    public Response buscarPorCategoria(@PathParam("id") Long idCategoria,
                                       @QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        return Response.ok(service.findByCategoria(idCategoria, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .build();
    }

    @GET
    @Path("/search/marca/{id: \\d+}")
    public Response buscarPorMarca(@PathParam("id") Long idMarca,
                                   @QueryParam("page") @DefaultValue("0") int page,
                                   @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        return Response.ok(service.findByMarca(idMarca, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .build();
    }

    @GET
    @Path("/count")
    public long total() {
        return service.count();
    }

    @POST
    @RolesAllowed("Adm")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response incluir(
            @RestForm String nome,
            @RestForm String descricao,
            @RestForm Double preco,
            @RestForm String modelo,
            @RestForm Boolean ativo,
            @RestForm Integer idSexo,
            @RestForm Long idCategoria,
            @RestForm Long idMarca,
            @RestForm Long idEstampa,
            @RestForm List<Long> idsMateriais,
            @RestForm String variantes,
            @RestForm("file") FileUpload imagem) throws IOException {

        List<PijamaVarianteRequestDTO> variantesList = parseVariantes(variantes);
        if (variantesList == null) {
            return Response.status(400)
                    .entity(Map.of("message", "JSON de variantes inválido.")).build();
        }

        PijamaDTO dto = new PijamaDTO(nome, descricao, preco, modelo, ativo,
                idSexo, idCategoria, idMarca, idEstampa, idsMateriais, variantesList);
        PijamaResponseDTO criado = service.create(dto);
        if (imagem != null && imagem.uploadedFile() != null && imagem.size() > 0) {
            service.adicionarImagem(criado.id(), imagem);
            criado = service.findById(criado.id());
        }
        URI location = UriBuilder.fromResource(PijamaResource.class)
                .path("{id}")
                .build(criado.id());
        return Response.created(location).entity(criado).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @RolesAllowed("Adm")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response alterar(
            @PathParam("id") Long id,
            @RestForm String nome,
            @RestForm String descricao,
            @RestForm Double preco,
            @RestForm String modelo,
            @RestForm Boolean ativo,
            @RestForm Integer idSexo,
            @RestForm Long idCategoria,
            @RestForm Long idMarca,
            @RestForm Long idEstampa,
            @RestForm List<Long> idsMateriais,
            @RestForm String variantes,
            @RestForm("file") FileUpload imagem) throws IOException {

        List<PijamaVarianteRequestDTO> variantesList = parseVariantes(variantes);
        if (variantesList == null) {
            return Response.status(400)
                    .entity(Map.of("message", "JSON de variantes inválido.")).build();
        }

        PijamaDTO dto = new PijamaDTO(nome, descricao, preco, modelo, ativo,
                idSexo, idCategoria, idMarca, idEstampa, idsMateriais, variantesList);
        PijamaResponseDTO atualizado = service.update(id, dto);
        if (imagem != null && imagem.uploadedFile() != null && imagem.size() > 0) {
            service.adicionarImagem(id, imagem);
            atualizado = service.findById(id);
        }
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    @RolesAllowed("Adm")
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id: \\d+}/imagens/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("Adm")
    public Response adicionarImagem(
            @PathParam("id")
            @NotNull(message = "id é obrigatório.")
            @Min(value = 1, message = "id deve ser maior ou igual a 1.")
            Long id,

            @RestForm("file")
            @NotNull(message = "Arquivo de imagem é obrigatório.")
            FileUpload file) {

        try {
            service.adicionarImagem(id, file);
            return Response.noContent().build();
        } catch (IOException e) {
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/imagens/download/{fid}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImagem(@PathParam("fid") String fid) {
        ArquivoDownload download = service.downloadImagem(fid);
        ResponseBuilder response = Response.ok(download.content(), download.contentType());
        response.header("Content-Disposition",
                "attachment; filename=\"" + download.fileName().replace("\"", "") + "\"");
        return response.build();
    }

    @DELETE
    @Path("/{id: \\d+}/imagens/{fid}")
    @RolesAllowed("Adm")
    public Response removerImagem(@PathParam("id") Long id, @PathParam("fid") String fid) {
        service.removerImagem(fid);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id: \\d+}/imagens/reordenar")
    @RolesAllowed("Adm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reordenarImagens(
            @PathParam("id") Long id,
            List<ReordenarImagemDTO> ordens) {
        service.reordenarImagens(id, ordens);
        return Response.ok().build();
    }

    private List<PijamaVarianteRequestDTO> parseVariantes(String json) {
        if (json == null || json.isBlank()) return List.of();
        try {
            return objectMapper.readValue(json,
                    new TypeReference<List<PijamaVarianteRequestDTO>>() {});
        } catch (IOException e) {
            return null;
        }
    }
}
