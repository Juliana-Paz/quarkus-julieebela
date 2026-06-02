package br.unitins.tp2.dto;

import br.unitins.tp2.model.Material;

public record MaterialResponseDTO(
    Long id,
    String nome,
    String descricao
) {
    public static MaterialResponseDTO valueOf(Material material) {
        return new MaterialResponseDTO(
            material.getId(),
            material.getNome(),
            material.getDescricao()
        );
    }
}
