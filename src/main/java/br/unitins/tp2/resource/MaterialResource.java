package br.unitins.tp2.resource;

import java.net.URI;

import br.unitins.tp2.dto.MaterialDTO;
import br.unitins.tp2.dto.MaterialResponseDTO;
import br.unitins.tp2.dto.PagedResponseDTO;
import br.unitins.tp2.service.MaterialService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    private static final int MAX_PAGE_SIZE = 100;

    @Inject
    MaterialService service;

    @GET
    public PagedResponseDTO<MaterialResponseDTO> buscarTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("25") int size) {
        page = Math.max(0, page);
        size = Math.min(Math.max(1, size), MAX_PAGE_SIZE);
        return new PagedResponseDTO<>(service.findAll(page, size), service.count(), page, size);
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
    @Path("/count")
    public long total() {
        return service.count();
    }

    @POST
    @RolesAllowed("Adm")
    public Response incluir(@Valid MaterialDTO dto) {
        MaterialResponseDTO criado = service.create(dto);
        URI location = UriBuilder.fromResource(MaterialResource.class)
                .path("{id}")
                .build(criado.id());
        return Response.created(location).entity(criado).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @RolesAllowed("Adm")
    public Response alterar(@PathParam("id") Long id, @Valid MaterialDTO dto) {
        return Response.ok(service.update(id, dto)).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    @RolesAllowed("Adm")
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}
