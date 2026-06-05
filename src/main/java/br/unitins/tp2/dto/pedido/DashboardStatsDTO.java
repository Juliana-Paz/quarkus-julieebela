package br.unitins.tp2.dto.pedido;

import java.util.List;

public record DashboardStatsDTO(
        long totalPedidos,
        double receitaTotal,
        double receitaMesAtual,
        long pedidosMesAtual,
        List<PijamaMaisVendidoDTO> maisVendidos,
        List<ReceitaMensalDTO> receitaMensal
) {}
