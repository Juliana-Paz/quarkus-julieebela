package br.unitins.tp2.resource;

import java.net.URI;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;
import br.unitins.tp2.dto.PedidoStatusDTO;
import br.unitins.tp2.dto.pedido.DashboardStatsDTO;
import br.unitins.tp2.model.StatusPedido;
import br.unitins.tp2.service.PedidoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final int MAX_PAGE_SIZE = 100;

    @Inject
    PedidoService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Authenticated
    public Response meuHistorico(@QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("pageSize") @DefaultValue("20") int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), MAX_PAGE_SIZE);

        String username = jwt.getSubject();
        return Response.ok(service.findByCliente(username, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .build();
    }

    @GET
    @Path("/{id: \\d+}")
    @Authenticated
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @Authenticated
    public Response criar(@Valid PedidoDTO dto) {
        String username = jwt.getSubject();
        PedidoResponseDTO criado = service.create(dto, username);
        URI location = UriBuilder.fromResource(PedidoResource.class)
                .path("{id}")
                .build(criado.id());
        return Response.created(location).entity(criado).build();
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("Adm")
    public DashboardStatsDTO getStats() {
        return service.getStats();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("Adm")
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

    @PATCH
    @Path("/{id: \\d+}/status")
    @RolesAllowed("Adm")
    public Response atualizarStatus(@PathParam("id") Long id, @Valid PedidoStatusDTO dto) {
        StatusPedido novoStatus = StatusPedido.valueOf(dto.idStatus());
        return Response.ok(service.updateStatus(id, novoStatus)).build();
    }

}
