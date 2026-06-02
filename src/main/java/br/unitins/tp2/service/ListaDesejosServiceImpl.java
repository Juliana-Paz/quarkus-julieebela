package br.unitins.tp2.service;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp2.dto.ListaDesejosResponseDTO;
import br.unitins.tp2.model.Cliente;
import br.unitins.tp2.model.ListaDesejos;
import br.unitins.tp2.model.Pijama;
import br.unitins.tp2.repository.ClienteRepository;
import br.unitins.tp2.repository.ListaDesejosRepository;
import br.unitins.tp2.repository.PijamaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ListaDesejosServiceImpl implements ListaDesejosService {

    @Inject
    ListaDesejosRepository listaDesejosRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PijamaRepository pijamaRepository;

    @Override
    public ListaDesejosResponseDTO findByCliente(String username) {
        ListaDesejos lista = resolverLista(username, false);
        if (lista == null) throw new NotFoundException("Lista de desejos não encontrada.");
        return ListaDesejosResponseDTO.valueOf(lista);
    }

    @Override
    @Transactional
    public ListaDesejosResponseDTO adicionarPijama(String username, Long idPijama) {
        ListaDesejos lista = resolverLista(username, true);

        Pijama pijama = pijamaRepository.findById(idPijama);
        if (pijama == null) throw new NotFoundException("Pijama não encontrado.");

        boolean jaExiste = lista.getPijamas().stream()
                .anyMatch(p -> p.getId().equals(idPijama));
        if (!jaExiste) {
            lista.getPijamas().add(pijama);
        }

        return ListaDesejosResponseDTO.valueOf(lista);
    }

    @Override
    @Transactional
    public ListaDesejosResponseDTO removerPijama(String username, Long idPijama) {
        ListaDesejos lista = resolverLista(username, false);
        if (lista == null) throw new NotFoundException("Lista de desejos não encontrada.");

        lista.getPijamas().removeIf(p -> p.getId().equals(idPijama));

        return ListaDesejosResponseDTO.valueOf(lista);
    }

    private ListaDesejos resolverLista(String username, boolean criarSeAusente) {
        Cliente cliente = clienteRepository.findByUsuarioUsername(username);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");

        List<ListaDesejos> listas = listaDesejosRepository.findByClienteId(cliente.getId());

        if (!listas.isEmpty()) {
            return listas.get(0);
        }

        if (!criarSeAusente) {
            return null;
        }

        ListaDesejos nova = new ListaDesejos();
        nova.setDataCriacao(LocalDate.now());
        nova.setCliente(cliente);
        listaDesejosRepository.persist(nova);
        return nova;
    }

}
