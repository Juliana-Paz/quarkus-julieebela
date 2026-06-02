package br.unitins.tp2.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp2.model.FormaPagamento;
import br.unitins.tp2.model.Pedido;
import br.unitins.tp2.model.StatusPedido;

public record PedidoResponseDTO(
    Long id,
    LocalDateTime data,
    Double total,
    Double valorDesconto,
    StatusPedido status,
    FormaPagamento formaPagamento,
    EnderecoResponseDTO enderecoEntrega,
    CupomResponseDTO cupom,
    List<ItemPedidoResponseDTO> itens
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getData(),
            pedido.getTotal(),
            pedido.getValorDesconto(),
            pedido.getStatus(),
            pedido.getFormaPagamento(),
            pedido.getEnderecoEntrega() != null
                    ? EnderecoResponseDTO.valueOf(pedido.getEnderecoEntrega()) : null,
            pedido.getCupom() != null ? CupomResponseDTO.valueOf(pedido.getCupom()) : null,
            pedido.getItens().stream().map(ItemPedidoResponseDTO::valueOf).toList()
        );
    }
}
