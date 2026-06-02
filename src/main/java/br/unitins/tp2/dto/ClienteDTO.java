package br.unitins.tp2.dto;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record ClienteDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,

    @NotBlank(message = "O CPF é obrigatório.")
    @Length(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
    String cpf,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Length(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    String email,

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data no passado.")
    LocalDate dataNascimento,

    @NotBlank(message = "O username é obrigatório.")
    String username,

    @NotBlank(message = "A senha é obrigatória.")
    @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    String senha,

    @Valid
    List<TelefoneDTO> telefones,

    @Valid
    List<EnderecoDTO> enderecos

) {}
