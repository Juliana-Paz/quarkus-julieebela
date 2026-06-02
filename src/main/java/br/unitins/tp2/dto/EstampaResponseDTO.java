package br.unitins.tp2.dto;

import br.unitins.tp2.model.Estampa;

public record EstampaResponseDTO(
    Long id,
    String nome
) {
    public static EstampaResponseDTO valueOf(Estampa estampa) {
        return new EstampaResponseDTO(
            estampa.getId(),
            estampa.getNome()
        );
    }
}
