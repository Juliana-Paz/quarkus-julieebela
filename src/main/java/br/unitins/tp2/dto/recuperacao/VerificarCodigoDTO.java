package br.unitins.tp2.dto.recuperacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerificarCodigoDTO(
        @NotBlank @Size(min = 6, max = 6) @Pattern(regexp = "\\d{6}") String codigo
) {}
