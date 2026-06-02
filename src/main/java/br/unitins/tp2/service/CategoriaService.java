package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.CategoriaDTO;
import br.unitins.tp2.dto.CategoriaResponseDTO;

public interface CategoriaService {

    CategoriaResponseDTO create(CategoriaDTO dto);

    CategoriaResponseDTO update(Long id, CategoriaDTO dto);

    void delete(Long id);

    CategoriaResponseDTO findById(Long id);

    List<CategoriaResponseDTO> findAll(int page, int pageSize);

    List<CategoriaResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);

}
