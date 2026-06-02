package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Categoria extends DefaultEntity {

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 255)
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
