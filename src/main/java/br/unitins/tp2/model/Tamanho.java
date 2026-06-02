package br.unitins.tp2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Tamanho {

    RN(1, "Recém-Nascido"),
    UM_A_TRES_MESES(2, "1 a 3 Meses"),
    TRES_A_SEIS_MESES(3, "3 a 6 Meses"),
    SEIS_A_NOVE_MESES(4, "6 a 9 Meses"),
    UM_ANO(5, "1 Ano"),
    DOIS_ANOS(6, "2 Anos"),
    TRES_ANOS(7, "3 Anos"),
    QUATRO_ANOS(8, "4 Anos"),
    SEIS_ANOS(9, "6 Anos"),
    OITO_ANOS(10, "8 Anos"),
    DEZ_ANOS(11, "10 Anos"),
    DOZE_ANOS(12, "12 Anos"),
    ADULTO(13, "Adulto");

    private final Integer id;
    private final String nome;

    Tamanho(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static Tamanho valueOf(Integer id) {
        if (id == null)
            return null;
        for (Tamanho tamanho : Tamanho.values()) {
            if (tamanho.getId().equals(id))
                return tamanho;
        }
        throw new IllegalArgumentException("Id inválido para Tamanho: " + id);
    }

}
