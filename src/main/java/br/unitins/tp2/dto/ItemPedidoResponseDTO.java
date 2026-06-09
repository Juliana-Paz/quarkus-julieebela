package br.unitins.tp2.dto;

import br.unitins.tp2.dto.pijama.PijamaVarianteResponseDTO;
import br.unitins.tp2.model.ItemPedido;

public record ItemPedidoResponseDTO(
    Long id,
    Integer quantidade,
    Double precoUnitario,
    Double subtotal,
    PijamaResponseDTO pijama,
    PijamaVarianteResponseDTO variante
) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        return new ItemPedidoResponseDTO(
            item.getId(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getSubtotal(),
            PijamaResponseDTO.valueOf(item.getPijama()),
            item.getVariante() != null ? PijamaVarianteResponseDTO.valueOf(item.getVariante()) : null
        );
    }
}
