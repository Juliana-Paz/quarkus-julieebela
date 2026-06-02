package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.MarcaDTO;
import br.unitins.tp2.dto.MarcaResponseDTO;

public interface MarcaService {

    MarcaResponseDTO create(MarcaDTO dto);

    MarcaResponseDTO update(Long id, MarcaDTO dto);

    void delete(Long id);

    MarcaResponseDTO findById(Long id);

    List<MarcaResponseDTO> findAll(int page, int pageSize);

    List<MarcaResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);

}
