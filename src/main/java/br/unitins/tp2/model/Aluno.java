package br.unitins.tp2.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Aluno extends DefaultEntity {

    @Column(length = 20, nullable = false)
    private String nome;

    @Column(length = 60, nullable = false)
    private String sobrenome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 11, nullable = false)
    private String cpf;

    @Column(length = 100, nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(
        name = "aluno_telefone",
        joinColumns = @JoinColumn(name = "id_aluno"),
        uniqueConstraints = @UniqueConstraint(
            name = "uk_aluno_codigo_area_numero",
            columnNames = {"id_aluno", "codigo_area", "numero"} 
        )
    )
    private List<Telefone> telefones;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

}