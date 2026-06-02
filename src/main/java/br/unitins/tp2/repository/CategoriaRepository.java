package br.unitins.tp2.repository;

import br.unitins.tp2.model.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    @Override
    public PanacheQuery<Categoria> findAll() {
        return find("SELECT c FROM Categoria c ORDER BY c.nome");
    }

    public PanacheQuery<Categoria> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

}
