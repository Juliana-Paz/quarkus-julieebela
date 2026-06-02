package br.unitins.tp2.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CorDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
    String nome,

    @NotBlank(message = "O hexadecimal é obrigatório.")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "O hexadecimal deve estar no formato #RRGGBB.")
    String hexadecimal

) {}
