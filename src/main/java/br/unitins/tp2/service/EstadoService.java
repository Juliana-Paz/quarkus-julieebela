package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.EstadoDTO;
import br.unitins.tp2.model.Estado;
import jakarta.validation.Valid;

public interface EstadoService {

    Estado create(@Valid EstadoDTO estado);
    void update(long id, EstadoDTO estado);
    void delete(long id);
    Estado findById(long id);
    Estado findBySigla(String sigla);
    List<Estado> findAll(Integer page, Integer pageSize);
    List<Estado> findByNome(String nome, Integer page, Integer pageSize);
    long count();
    long count(String nome);
    
}
