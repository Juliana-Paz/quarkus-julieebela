package br.unitins.tp2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanoDTO(
    @NotBlank(message = "nome é obrigatório")
    String nome,

    @NotNull(message = "maxAlunos é obrigatório")
    @Positive(message = "maxAlunos deve ser maior que zero")
    Integer maxAlunos,

    @NotNull(message = "maxProfessores é obrigatório")
    @Positive(message = "maxProfessores deve ser maior que zero")
    Integer maxProfessores,

    @NotNull(message = "precoMensal é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "precoMensal deve ser maior que zero")
    Double precoMensal,

    @NotNull(message = "descontoAnual é obrigatório")
    @DecimalMin(value = "0.0", message = "descontoAnual deve ser maior ou igual a zero")
    Double descontoAnual
) { }