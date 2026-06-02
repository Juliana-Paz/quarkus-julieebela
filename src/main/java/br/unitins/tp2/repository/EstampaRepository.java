package br.unitins.tp2.repository;

import br.unitins.tp2.model.Estampa;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstampaRepository implements PanacheRepository<Estampa> {

    @Override
    public PanacheQuery<Estampa> findAll() {
        return find("SELECT e FROM Estampa e ORDER BY e.nome");
    }

    public PanacheQuery<Estampa> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

}
