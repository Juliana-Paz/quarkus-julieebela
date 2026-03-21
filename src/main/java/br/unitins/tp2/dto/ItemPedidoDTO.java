package br.unitins.tp2.dto;

public record ItemPedidoDTO(
    Integer quantidade,
    Double preco,
    Long idPlano
) { }