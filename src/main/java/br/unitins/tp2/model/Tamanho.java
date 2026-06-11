package br.unitins.tp2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Tamanho {

    RN(1, "RN"),
    P(2, "P"),
    M(3, "M"),
    G(4, "G"),
    GG(5, "GG"),
    XG(6, "XG"),
    ADULTO(7, "Adulto"),
    UM_ANO(8, "1"),
    DOIS_ANOS(9, "2"),
    QUATRO_ANOS(10, "4"),
    SEIS_ANOS(11, "6"),
    OITO_ANOS(12, "8"),
    DEZ_ANOS(13, "10"),
    DOZE_ANOS(14, "12"),
    DEZESSEIS_ANOS(15, "16");

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
