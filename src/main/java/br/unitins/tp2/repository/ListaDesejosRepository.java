package br.unitins.tp2.repository;

import java.util.List;

import br.unitins.tp2.model.ListaDesejos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListaDesejosRepository implements PanacheRepository<ListaDesejos> {

    public List<ListaDesejos> findByClienteId(Long clienteId) {
        return find("cliente.id = ?1", clienteId).list();
    }

}
