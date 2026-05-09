package br.unitins.tp2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

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

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "plano_arquivo", joinColumns = @JoinColumn(name = "plano_id"), inverseJoinColumns = @JoinColumn(name = "arquivo_id", unique = true))
    private List<Arquivo> arquivos = new ArrayList<>();

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

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public void addArquivo(Arquivo arquivo) {
        if (arquivo == null) {
            return;
        }
        arquivos.add(arquivo);
    }

    public void removeArquivo(Arquivo arquivo) {
        if (arquivo == null) {
            return;
        }
        arquivos.remove(arquivo);
    }

}
