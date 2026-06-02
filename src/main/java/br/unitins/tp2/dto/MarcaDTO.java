package br.unitins.tp2.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record MarcaDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,

    @Length(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    String descricao

) {}
