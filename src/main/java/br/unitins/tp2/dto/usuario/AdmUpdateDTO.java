package br.unitins.tp2.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdmUpdateDTO(
        @NotBlank String nome,
        @NotBlank String username,
        String senhaAtual,
        @Size(min = 6) String novaSenha
) {}
