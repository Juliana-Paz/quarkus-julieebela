package br.unitins.tp2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class ListaDesejos extends DefaultEntity {

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "lista_desejos_pijama",
        joinColumns = @JoinColumn(name = "lista_desejos_id"),
        inverseJoinColumns = @JoinColumn(name = "pijama_id")
    )
    private List<Pijama> pijamas = new ArrayList<>();

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pijama> getPijamas() {
        return pijamas;
    }

    public void setPijamas(List<Pijama> pijamas) {
        this.pijamas = pijamas;
    }

}
