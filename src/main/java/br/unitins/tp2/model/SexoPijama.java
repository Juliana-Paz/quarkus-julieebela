package br.unitins.tp2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexoPijama {

    FEMININO(1, "Feminino"),
    MASCULINO(2, "Masculino"),
    UNISSEX(3, "Unissex");

    private final Integer id;
    private final String nome;

    SexoPijama(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static SexoPijama valueOf(Integer id) {
        if (id == null)
            return null;
        for (SexoPijama sexo : SexoPijama.values()) {
            if (sexo.getId().equals(id))
                return sexo;
        }
        throw new IllegalArgumentException("Id inválido para SexoPijama: " + id);
    }

}
