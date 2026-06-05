package br.unitins.tp2.resource;

import br.unitins.tp2.dto.recuperacao.RedefinirSenhaDTO;
import br.unitins.tp2.dto.recuperacao.SolicitarRecuperacaoDTO;
import br.unitins.tp2.dto.recuperacao.TokenTemporarioDTO;
import br.unitins.tp2.dto.recuperacao.VerificarCodigoDTO;
import br.unitins.tp2.service.RecuperacaoSenhaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recuperacao-senha")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecuperacaoSenhaResource {

    @Inject
    RecuperacaoSenhaService recuperacaoSenhaService;

    @POST
    @Path("/solicitar")
    public Response solicitar(@Valid SolicitarRecuperacaoDTO dto) {
        recuperacaoSenhaService.solicitarRecuperacao(dto);
        return Response.ok(java.util.Map.of("message", "Código enviado para o e-mail informado.")).build();
    }

    @POST
    @Path("/verificar-codigo")
    public TokenTemporarioDTO verificarCodigo(@Valid VerificarCodigoDTO dto) {
        return recuperacaoSenhaService.verificarCodigo(dto);
    }

    @POST
    @Path("/redefinir")
    public Response redefinir(@Valid RedefinirSenhaDTO dto) {
        recuperacaoSenhaService.redefinirSenha(dto);
        return Response.noContent().build();
    }
}
