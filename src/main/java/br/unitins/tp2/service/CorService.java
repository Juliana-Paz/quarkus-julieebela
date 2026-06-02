package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.CorDTO;
import br.unitins.tp2.dto.CorResponseDTO;

public interface CorService {

    CorResponseDTO create(CorDTO dto);

    CorResponseDTO update(Long id, CorDTO dto);

    void delete(Long id);

    CorResponseDTO findById(Long id);

    List<CorResponseDTO> findAll(int page, int pageSize);

    List<CorResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);

}
