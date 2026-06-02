package br.unitins.tp2.service;

import br.unitins.tp2.dto.ListaDesejosResponseDTO;

public interface ListaDesejosService {

    ListaDesejosResponseDTO findByCliente(String username);

    ListaDesejosResponseDTO adicionarPijama(String username, Long idPijama);

    ListaDesejosResponseDTO removerPijama(String username, Long idPijama);

}
