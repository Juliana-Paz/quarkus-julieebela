package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.PlanoDTO;
import br.unitins.tp2.dto.PlanoResponseDTO;


public interface PlanoService {

        // recursos basicos
        List<PlanoResponseDTO> getAll(int page, int pageSize);

        PlanoResponseDTO findById(Long id);
    
        PlanoResponseDTO create(PlanoDTO dto);
    
        PlanoResponseDTO update(Long id, PlanoDTO dto);
    
        void delete(Long id);
    
        // recursos extras
    
        List<PlanoResponseDTO> findByNome(String nome, int page, int pageSize);
    
        long count();

        long countNome(String nome);
    
}