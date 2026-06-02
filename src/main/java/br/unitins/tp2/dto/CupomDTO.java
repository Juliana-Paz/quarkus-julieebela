package br.unitins.tp2.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CupomDTO(

    @NotBlank(message = "O código é obrigatório.")
    @Length(max = 20, message = "O código deve ter no máximo 20 caracteres.")
    String codigo,

    @Length(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    String descricao,

    @DecimalMin(value = "0.0", inclusive = false, message = "O valor do desconto deve ser maior que zero.")
    Double valorDesconto,

    @NotNull(message = "O campo percentual é obrigatório.")
    Boolean percentual,

    @NotNull(message = "A data de início é obrigatória.")
    LocalDate dataInicio,

    @NotNull(message = "A data de fim é obrigatória.")
    LocalDate dataFim,

    @NotNull(message = "O campo ativo é obrigatório.")
    Boolean ativo

) {}
