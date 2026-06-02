package br.unitins.tp2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPedido {

    AGUARDANDO_PAGAMENTO(1, "Aguardando Pagamento"),
    PAGO(2, "Pago"),
    EM_SEPARACAO(3, "Em Separação"),
    ENVIADO(4, "Enviado"),
    ENTREGUE(5, "Entregue"),
    CANCELADO(6, "Cancelado");

    private final Integer id;
    private final String nome;

    StatusPedido(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static StatusPedido valueOf(Integer id) {
        if (id == null)
            return null;
        for (StatusPedido status : StatusPedido.values()) {
            if (status.getId().equals(id))
                return status;
        }
        throw new IllegalArgumentException("Id inválido para StatusPedido: " + id);
    }

}
