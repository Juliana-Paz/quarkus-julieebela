package br.unitins.tp2.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(

    @NotBlank(message = "O logradouro é obrigatório.")
    @Length(max = 150, message = "O logradouro deve ter no máximo 150 caracteres.")
    String logradouro,

    @NotBlank(message = "O número é obrigatório.")
    @Length(max = 10, message = "O número deve ter no máximo 10 caracteres.")
    String numero,

    @Length(max = 60, message = "O complemento deve ter no máximo 60 caracteres.")
    String complemento,

    @NotBlank(message = "O bairro é obrigatório.")
    @Length(max = 80, message = "O bairro deve ter no máximo 80 caracteres.")
    String bairro,

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP deve estar no formato XXXXX-XXX ou XXXXXXXX.")
    String cep,

    @NotBlank(message = "O município é obrigatório.")
    @Length(max = 80, message = "O município deve ter no máximo 80 caracteres.")
    String municipio,

    @NotBlank(message = "O estado é obrigatório.")
    @Length(max = 60, message = "O estado deve ter no máximo 60 caracteres.")
    String estado,

    @NotNull(message = "O campo principal é obrigatório.")
    Boolean principal

) {}
