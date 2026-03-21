package br.unitins.tp2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Assinatura extends DefaultEntity {

    // Datas como LocalDate (conforme seu pedido)
    @Column(nullable = false)
    private LocalDate inicio;

    @Column
    private LocalDate fim;

    @Column(nullable = false)
    private Boolean planoMensal;

    @ManyToOne()
    @JoinColumn(name = "id_plano", nullable = false)
    private Plano plano;

    @OneToMany(mappedBy = "assinatura", cascade = CascadeType.ALL)
    private List<Fatura> faturas = new ArrayList<>();

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

}
