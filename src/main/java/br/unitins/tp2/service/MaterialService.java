package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.MaterialDTO;
import br.unitins.tp2.dto.MaterialResponseDTO;

public interface MaterialService {

    MaterialResponseDTO create(MaterialDTO dto);

    MaterialResponseDTO update(Long id, MaterialDTO dto);

    void delete(Long id);

    MaterialResponseDTO findById(Long id);

    List<MaterialResponseDTO> findAll(int page, int pageSize);

    List<MaterialResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);

}
