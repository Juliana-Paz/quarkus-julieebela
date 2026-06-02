package br.unitins.tp2.resource;

import java.net.URI;
import java.util.List;

import br.unitins.tp2.dto.MarcaDTO;
import br.unitins.tp2.dto.MarcaResponseDTO;
import br.unitins.tp2.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    private static final int MAX_PAGE_SIZE = 100;

    @Inject
    MarcaService service;

    @GET
    public Response buscarTodos(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        List<MarcaResponseDTO> lista = service.findAll(page, pageSize);
        return Response.ok(lista)
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
    @Path("/count")
    public long total() {
        return service.count();
    }

    @POST
    @RolesAllowed("Adm")
    public Response incluir(@Valid MarcaDTO dto) {
        MarcaResponseDTO criada = service.create(dto);
        URI location = UriBuilder.fromResource(MarcaResource.class)
                .path("{id}")
                .build(criada.id());
        return Response.created(location).entity(criada).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @RolesAllowed("Adm")
    public Response alterar(@PathParam("id") Long id, @Valid MarcaDTO dto) {
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
