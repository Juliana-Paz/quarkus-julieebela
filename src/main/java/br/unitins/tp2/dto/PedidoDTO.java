package br.unitins.tp2.dto;

import java.util.List;

public record PedidoDTO(
    Double total,
    List<ItemPedidoDTO> itensPedido
) { }