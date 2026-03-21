package br.unitins.tp2.dto;

import br.unitins.tp2.model.Plano;

public record PlanoResponseDTO(
    Long id,
    String nome,
    Integer maxAlunos,
    Integer maxProfessores,
    Double precoMensal,
    Double descontoAnual,
    String nomeImagem
    ) {

public static PlanoResponseDTO valueOf(Plano plano) {
    return new PlanoResponseDTO(
        plano.getId(),
        plano.getNome(),
        plano.getMaxAlunos(),
        plano.getMaxProfessores(),
        plano.getPrecoMensal(),
        plano.getDescontoAnual(),
        plano.getNomeImagem()
    );
}

}
