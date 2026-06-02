package br.unitins.tp2.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp2.model.ListaDesejos;

public record ListaDesejosResponseDTO(
    Long id,
    LocalDate dataCriacao,
    List<PijamaResponseDTO> pijamas
) {
    public static ListaDesejosResponseDTO valueOf(ListaDesejos lista) {
        return new ListaDesejosResponseDTO(
            lista.getId(),
            lista.getDataCriacao(),
            lista.getPijamas().stream().map(PijamaResponseDTO::valueOf).toList()
        );
    }
}
