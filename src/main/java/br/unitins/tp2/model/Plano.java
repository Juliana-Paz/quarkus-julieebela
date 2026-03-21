package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Plano extends DefaultEntity {

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, name = "max_alunos")
    private Integer maxAlunos;

    @Column(nullable = false, name = "max_professores")
    private Integer maxProfessores;

    @Column(nullable = false, name = "preco_mensal")
    private Double precoMensal;

    @Column(nullable = false, name = "desconto_anual")
    private Double descontoAnual;

    @Column(nullable = true, name = "nome_imagem")
    private String nomeImagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMaxAlunos() {
        return maxAlunos;
    }

    public void setMaxAlunos(Integer maxAlunos) {
        this.maxAlunos = maxAlunos;
    }

    public Integer getMaxProfessores() {
        return maxProfessores;
    }

    public void setMaxProfessores(Integer maxProfessores) {
        this.maxProfessores = maxProfessores;
    }

    public Double getPrecoMensal() {
        return precoMensal;
    }

    public void setPrecoMensal(Double precoMensal) {
        this.precoMensal = precoMensal;
    }

    public Double getDescontoAnual() {
        return descontoAnual;
    }

    public void setDescontoAnual(Double descontoAnual) {
        this.descontoAnual = descontoAnual;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

}
