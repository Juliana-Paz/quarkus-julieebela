package br.unitins.tp2.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(

    @NotNull(message = "A forma de pagamento é obrigatória.")
    Integer idFormaPagamento,

    @NotNull(message = "O endereço de entrega é obrigatório.")
    EnderecoDTO enderecoEntrega,

    String codigoCupom,

    @NotEmpty(message = "O pedido deve ter ao menos um item.")
    @Valid
    List<ItemPedidoDTO> itens

) {}
