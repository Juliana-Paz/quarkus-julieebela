package br.unitins.tp2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoDTO(

    @NotNull(message = "O pijama é obrigatório.")
    Long idPijama,

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    @Min(value = 1, message = "A quantidade mínima é 1.")
    Integer quantidade

) {}
