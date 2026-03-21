package br.unitins.tp2.resource;

import br.unitins.tp2.dto.MunicipioDTO;
import br.unitins.tp2.dto.MunicipioResponseDTO;
import br.unitins.tp2.service.MunicipioService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import jakarta.ws.rs.core.Response.Status;

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService service;

    @GET
    public Response buscarTodos(@QueryParam("page") @DefaultValue("0") int page, 
                                @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        
        // garantindo o numero maximo de registros por consulta                           
        if (pageSize > 100)
            pageSize = 100;
        return Response.ok(service.getAll(page, pageSize)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page") @DefaultValue("0") int page, 
                                  @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(service.findByNome(nome, page, pageSize)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }
    
    @POST
    public Response incluir(MunicipioDTO dto) {
        MunicipioResponseDTO retorno = service.create(dto);
        return Response.status(201).entity(retorno).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(MunicipioDTO dto, @PathParam("id") Long id) {
        service.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long total() {
        return service.count();
    }

}