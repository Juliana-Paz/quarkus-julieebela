package br.unitins.tp2.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CupomDesconto extends DefaultEntity {

    @Column(length = 20, nullable = false, unique = true)
    private String codigo;

    @Column(length = 255)
    private String descricao;

    @Column(name = "valor_desconto")
    private Double valorDesconto;

    @Column(nullable = false)
    private Boolean percentual;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private Boolean ativo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Boolean getPercentual() {
        return percentual;
    }

    public void setPercentual(Boolean percentual) {
        this.percentual = percentual;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

}
