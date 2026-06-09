package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PijamaVariante extends DefaultEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "pijama_id", nullable = false)
    private Pijama pijama;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tamanho tamanho;

    @ManyToOne
    @JoinColumn(name = "cor_id")
    private Cor cor;

    @Column(nullable = false)
    private Integer estoque;

    public Pijama getPijama() { return pijama; }
    public void setPijama(Pijama pijama) { this.pijama = pijama; }

    public Tamanho getTamanho() { return tamanho; }
    public void setTamanho(Tamanho tamanho) { this.tamanho = tamanho; }

    public Cor getCor() { return cor; }
    public void setCor(Cor cor) { this.cor = cor; }

    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
}
