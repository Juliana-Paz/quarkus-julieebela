package br.unitins.tp2.repository;

import br.unitins.tp2.model.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno> {

    @Override
    public PanacheQuery<Aluno> findAll() {
        return find("SELECT a FROM Aluno a ORDER BY a.nome ");
    }

    public PanacheQuery<Aluno> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
    }

    public long countNome(String nome) {
        return count("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
    }

}
