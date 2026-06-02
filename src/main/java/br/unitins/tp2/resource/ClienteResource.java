package br.unitins.tp2.resource;

import java.net.URI;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp2.dto.ClienteAlterarSenhaDTO;
import br.unitins.tp2.dto.ClienteDTO;
import br.unitins.tp2.dto.ClienteResponseDTO;
import br.unitins.tp2.dto.ClienteUpdateDTO;
import br.unitins.tp2.dto.EnderecoDTO;
import br.unitins.tp2.dto.TelefoneDTO;
import br.unitins.tp2.service.ClienteService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @Inject
    JsonWebToken jwt;

    @POST
    public Response cadastrar(@Valid ClienteDTO dto) {
        ClienteResponseDTO criado = service.create(dto);
        URI location = UriBuilder.fromResource(ClienteResource.class)
                .path("me")
                .build();
        return Response.created(location).entity(criado).build();
    }

    @GET
    @Path("/me")
    @Authenticated
    public Response meuPerfil() {
        String username = jwt.getSubject();
        return Response.ok(service.findByUsername(username)).build();
    }

    @PUT
    @Path("/me")
    @Authenticated
    public Response atualizar(@Valid ClienteUpdateDTO dto) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        return Response.ok(service.update(me.id(), dto)).build();
    }

    @PATCH
    @Path("/me/senha")
    @Authenticated
    public Response alterarSenha(@Valid ClienteAlterarSenhaDTO dto) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        service.alterarSenha(me.id(), dto);
        return Response.noContent().build();
    }

    @POST
    @Path("/me/enderecos")
    @Authenticated
    public Response adicionarEndereco(@Valid EnderecoDTO dto) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        return Response.ok(service.adicionarEndereco(me.id(), dto)).build();
    }

    @DELETE
    @Path("/me/enderecos/{index: \\d+}")
    @Authenticated
    public Response removerEndereco(@PathParam("index") int index) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        return Response.ok(service.removerEndereco(me.id(), index)).build();
    }

    @POST
    @Path("/me/telefones")
    @Authenticated
    public Response adicionarTelefone(@Valid TelefoneDTO dto) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        return Response.ok(service.adicionarTelefone(me.id(), dto)).build();
    }

    @DELETE
    @Path("/me/telefones/{index: \\d+}")
    @Authenticated
    public Response removerTelefone(@PathParam("index") int index) {
        String username = jwt.getSubject();
        ClienteResponseDTO me = service.findByUsername(username);
        return Response.ok(service.removerTelefone(me.id(), index)).build();
    }

}
