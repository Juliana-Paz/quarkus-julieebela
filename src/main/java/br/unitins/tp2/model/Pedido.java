package br.unitins.tp2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido extends DefaultEntity {

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private Double total;

    @Column(name = "valor_desconto")
    private Double valorDesconto;

    private StatusPedido status;

    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "logradouro",  column = @Column(name = "entrega_logradouro",  length = 150, nullable = false)),
        @AttributeOverride(name = "numero",      column = @Column(name = "entrega_numero",      length = 10,  nullable = false)),
        @AttributeOverride(name = "complemento", column = @Column(name = "entrega_complemento", length = 60)),
        @AttributeOverride(name = "bairro",      column = @Column(name = "entrega_bairro",      length = 80,  nullable = false)),
        @AttributeOverride(name = "cep",         column = @Column(name = "entrega_cep",         length = 9,   nullable = false)),
        @AttributeOverride(name = "municipio",   column = @Column(name = "entrega_municipio",   length = 80,  nullable = false)),
        @AttributeOverride(name = "estado",      column = @Column(name = "entrega_estado",      length = 60,  nullable = false)),
        @AttributeOverride(name = "principal",   column = @Column(name = "entrega_principal"))
    })
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private CupomDesconto cupom;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens = new ArrayList<>();

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public CupomDesconto getCupom() {
        return cupom;
    }

    public void setCupom(CupomDesconto cupom) {
        this.cupom = cupom;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

}
