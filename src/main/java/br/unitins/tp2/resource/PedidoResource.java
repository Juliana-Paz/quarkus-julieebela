package br.unitins.tp2.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;
import br.unitins.tp2.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response buscarTodos() {
        return Response.ok(service.getAll()).build();
    }

    @POST
    @RolesAllowed("User")
    public Response incluir(PedidoDTO dto) {
        // obtendo o usuario do token
        String username = jwt.getSubject();

        PedidoResponseDTO retorno = service.create(dto, username);

        return Response.status(201).entity(retorno).build();
    }

}