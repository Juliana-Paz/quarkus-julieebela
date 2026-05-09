package br.unitins.tp2.dto;

import java.util.List;

import br.unitins.tp2.model.Plano;

public record PlanoResponseDTO(
    Long id,
    String nome,
    Integer maxAlunos,
    Integer maxProfessores,
    Double precoMensal,
    Double descontoAnual,
    List<ArquivoResponseDTO> imagens
    ) {

public static PlanoResponseDTO valueOf(Plano plano) {
    return new PlanoResponseDTO(
        plano.getId(),
        plano.getNome(),
        plano.getMaxAlunos(),
        plano.getMaxProfessores(),
        plano.getPrecoMensal(),
        plano.getDescontoAnual(),
        plano.getArquivos().stream().map(ArquivoResponseDTO::valueOf).toList()
    );
}

}
