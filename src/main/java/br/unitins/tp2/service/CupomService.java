package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.CupomDTO;
import br.unitins.tp2.dto.CupomResponseDTO;

public interface CupomService {

    CupomResponseDTO create(CupomDTO dto);

    CupomResponseDTO update(Long id, CupomDTO dto);

    void delete(Long id);

    CupomResponseDTO findById(Long id);

    List<CupomResponseDTO> findAll(int page, int pageSize);

    CupomResponseDTO findByCodigo(String codigo);

    List<CupomResponseDTO> findAtivos(int page, int pageSize);

    CupomResponseDTO validarCupom(String codigo);

    long count();

}
