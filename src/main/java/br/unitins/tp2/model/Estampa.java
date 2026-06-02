package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Estampa extends DefaultEntity {

    @Column(length = 100, nullable = false)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
