package br.unitins.tp2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormaPagamento {

    PIX(1, "Pix"),
    BOLETO(2, "Boleto"),
    CARTAO_CREDITO(3, "Cartão de Crédito"),
    CARTAO_DEBITO(4, "Cartão de Débito");

    private final Integer id;
    private final String nome;

    FormaPagamento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static FormaPagamento valueOf(Integer id) {
        if (id == null)
            return null;
        for (FormaPagamento forma : FormaPagamento.values()) {
            if (forma.getId().equals(id))
                return forma;
        }
        throw new IllegalArgumentException("Id inválido para FormaPagamento: " + id);
    }

}
