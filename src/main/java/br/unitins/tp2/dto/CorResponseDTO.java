package br.unitins.tp2.dto;

import br.unitins.tp2.model.Cor;

public record CorResponseDTO(
    Long id,
    String nome,
    String hexadecimal
) {
    public static CorResponseDTO valueOf(Cor cor) {
        return new CorResponseDTO(
            cor.getId(),
            cor.getNome(),
            cor.getHexadecimal()
        );
    }
}
