package br.unitins.tp2.repository;

import br.unitins.tp2.model.Material;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material> {

    @Override
    public PanacheQuery<Material> findAll() {
        return find("SELECT m FROM Material m ORDER BY m.nome");
    }

    public PanacheQuery<Material> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

}
