package br.unitins.tp2.dto;

import java.time.LocalDate;

import br.unitins.tp2.model.CupomDesconto;

public record CupomResponseDTO(
    Long id,
    String codigo,
    String descricao,
    Double valorDesconto,
    Boolean percentual,
    LocalDate dataInicio,
    LocalDate dataFim,
    Boolean ativo
) {
    public static CupomResponseDTO valueOf(CupomDesconto cupom) {
        return new CupomResponseDTO(
            cupom.getId(),
            cupom.getCodigo(),
            cupom.getDescricao(),
            cupom.getValorDesconto(),
            cupom.getPercentual(),
            cupom.getDataInicio(),
            cupom.getDataFim(),
            cupom.getAtivo()
        );
    }
}
