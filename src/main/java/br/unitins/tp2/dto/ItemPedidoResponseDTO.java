package br.unitins.tp2.dto;

import br.unitins.tp2.model.ItemPedido;

public record ItemPedidoResponseDTO(
    Long id,
    Integer quantidade,
    Double precoUnitario,
    Double subtotal,
    PijamaResponseDTO pijama
) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getSubtotal(),
            PijamaResponseDTO.valueOf(item.getPijama())
        );
    }
}
