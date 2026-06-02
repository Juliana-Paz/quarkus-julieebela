package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Cor extends DefaultEntity {

    @Column(length = 60, nullable = false)
    private String nome;

    @Column(length = 7, nullable = false)
    private String hexadecimal;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

}
