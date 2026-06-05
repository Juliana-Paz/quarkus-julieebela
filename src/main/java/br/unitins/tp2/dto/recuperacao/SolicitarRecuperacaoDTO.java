package br.unitins.tp2.dto.recuperacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarRecuperacaoDTO(
        @NotBlank @Email String email
) {}
