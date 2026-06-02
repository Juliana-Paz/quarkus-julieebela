package br.unitins.tp2.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp2.service.ListaDesejosService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ListaDesejosResource {

    @Inject
    ListaDesejosService service;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response buscarMinhaLista() {
        String username = jwt.getSubject();
        return Response.ok(service.findByCliente(username)).build();
    }

    @POST
    @Path("/{idPijama: \\d+}")
    public Response adicionarPijama(@PathParam("idPijama") Long idPijama) {
        String username = jwt.getSubject();
        return Response.ok(service.adicionarPijama(username, idPijama)).build();
    }

    @DELETE
    @Path("/{idPijama: \\d+}")
    public Response removerPijama(@PathParam("idPijama") Long idPijama) {
        String username = jwt.getSubject();
        return Response.ok(service.removerPijama(username, idPijama)).build();
    }

}
