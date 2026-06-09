package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity {

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private Double precoUnitario;

    @Column(nullable = false)
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "pijama_id", nullable = false)
    private Pijama pijama;

    @ManyToOne
    @JoinColumn(name = "variante_id")
    private PijamaVariante variante;

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Pijama getPijama() { return pijama; }
    public void setPijama(Pijama pijama) { this.pijama = pijama; }

    public PijamaVariante getVariante() { return variante; }
    public void setVariante(PijamaVariante variante) { this.variante = variante; }
}
