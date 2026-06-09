package br.unitins.tp2.service;

import br.unitins.tp2.dto.recuperacao.RedefinirSenhaDTO;
import br.unitins.tp2.dto.recuperacao.SolicitarRecuperacaoDTO;
import br.unitins.tp2.dto.recuperacao.TokenTemporarioDTO;
import br.unitins.tp2.dto.recuperacao.VerificarCodigoDTO;

public interface RecuperacaoSenhaService {
    void solicitarRecuperacao(SolicitarRecuperacaoDTO dto);
    TokenTemporarioDTO verificarCodigo(VerificarCodigoDTO dto);
    void redefinirSenha(RedefinirSenhaDTO dto);
}
