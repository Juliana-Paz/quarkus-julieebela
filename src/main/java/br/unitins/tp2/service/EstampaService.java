package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.EstampaDTO;
import br.unitins.tp2.dto.EstampaResponseDTO;

public interface EstampaService {

    EstampaResponseDTO create(EstampaDTO dto);

    EstampaResponseDTO update(Long id, EstampaDTO dto);

    void delete(Long id);

    EstampaResponseDTO findById(Long id);

    List<EstampaResponseDTO> findAll(int page, int pageSize);

    List<EstampaResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);

}
