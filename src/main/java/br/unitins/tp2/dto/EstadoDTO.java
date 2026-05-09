package br.unitins.tp2.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class EstadoDTO {

    @Length(min = 2, max = 60, message = "O campo deve conter entre 2 e 60 caracteres (back).")
    @NotBlank(message = "O campo deve ser informado (back).")
    private final String nome;

    @Length(min = 2, max = 2, message = "O campo deve ter 2 caracteres (back).")
    @NotBlank(message = "O campo deve ser informado (back).")
    private final String sigla;

    @NotNull(message = "O campo deve ser informado (back).")
    private final Long idRegiao;

    public EstadoDTO(String nome, String sigla, Long idRegiao) {
        this.nome = nome;
        this.sigla = sigla;
        this.idRegiao = idRegiao;
    }

    public Long getIdRegiao() {
        return idRegiao;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}
