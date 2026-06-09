package br.unitins.tp2.dto.recuperacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RedefinirSenhaDTO(
        @NotBlank String tokenTemporario,
        @NotBlank @Size(min = 6) String novaSenha
) {}
