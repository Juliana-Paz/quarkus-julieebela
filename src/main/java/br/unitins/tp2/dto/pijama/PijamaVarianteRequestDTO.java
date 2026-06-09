package br.unitins.tp2.dto.pijama;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PijamaVarianteRequestDTO(
    @NotNull Integer idTamanho,
    Long idCor,
    @NotNull @Min(0) Integer estoque
) {}
