package br.unitins.tp2.repository;

import br.unitins.tp2.model.Marca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    @Override
    public PanacheQuery<Marca> findAll() {
        return find("SELECT m FROM Marca m ORDER BY m.nome");
    }

    public PanacheQuery<Marca> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

}
