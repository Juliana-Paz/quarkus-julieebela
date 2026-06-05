package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;
import br.unitins.tp2.dto.pedido.DashboardStatsDTO;
import br.unitins.tp2.model.StatusPedido;

public interface PedidoService {

    PedidoResponseDTO create(PedidoDTO dto, String username);

    PedidoResponseDTO findById(Long id);

    List<PedidoResponseDTO> findByCliente(String username, int page, int pageSize);

    List<PedidoResponseDTO> findAll(int page, int pageSize);

    long count();

    PedidoResponseDTO updateStatus(Long id, StatusPedido novoStatus);

    DashboardStatsDTO getStats();

}
