package br.unitins.tp2.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record EstampaDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome

) {}
