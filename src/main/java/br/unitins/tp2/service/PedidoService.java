package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;


public interface PedidoService {

        List<PedidoResponseDTO> getAll();

        PedidoResponseDTO findById(Long id);

        List<PedidoResponseDTO> findByUsuario(String username);
    
        PedidoResponseDTO create(PedidoDTO dto, String username);
    
}