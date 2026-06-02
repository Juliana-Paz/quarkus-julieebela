package br.unitins.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(length = 150, nullable = false)
    private String logradouro;

    @Column(length = 10, nullable = false)
    private String numero;

    @Column(length = 60)
    private String complemento;

    @Column(length = 80, nullable = false)
    private String bairro;

    @Column(length = 9, nullable = false)
    private String cep;

    @Column(length = 80, nullable = false)
    private String municipio;

    @Column(length = 60, nullable = false)
    private String estado;

    @Column(nullable = false)
    private Boolean principal;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

}
