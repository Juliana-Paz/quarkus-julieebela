package br.unitins.tp2.dto;

import jakarta.validation.constraints.NotNull;

public record PedidoStatusDTO(

    @NotNull(message = "O status é obrigatório.")
    Integer idStatus

) {}
