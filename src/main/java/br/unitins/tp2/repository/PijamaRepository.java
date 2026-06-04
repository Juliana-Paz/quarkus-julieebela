package br.unitins.tp2.repository;

import br.unitins.tp2.model.Pijama;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PijamaRepository implements PanacheRepository<Pijama> {

    @Override
    public PanacheQuery<Pijama> findAll() {
        return find("SELECT p FROM Pijama p ORDER BY p.nome");
    }

    public PanacheQuery<Pijama> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public long countByNome(String nome) {
        return count("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public PanacheQuery<Pijama> findByCategoria(Long categoriaId) {
        return find("SELECT p FROM Pijama p WHERE p.categoria.id = ?1 ORDER BY p.nome", categoriaId);
    }

    public PanacheQuery<Pijama> findByMarca(Long marcaId) {
        return find("SELECT p FROM Pijama p WHERE p.marca.id = ?1 ORDER BY p.nome", marcaId);
    }

    public java.util.Optional<Pijama> findByArquivoId(Long arquivoId) {
        return find("SELECT p FROM Pijama p JOIN p.imagens a WHERE a.id = ?1", arquivoId).firstResultOptional();
    }

    public long countByCategoria(Long categoriaId) {
        return count("categoria.id", categoriaId);
    }

    public long countByMarca(Long marcaId) {
        return count("marca.id", marcaId);
    }

    public long countByEstampa(Long estampaId) {
        return count("estampa.id", estampaId);
    }

    public boolean existsByCor(Long corId) {
        return count("SELECT COUNT(p) FROM Pijama p JOIN p.cores c WHERE c.id = ?1", corId) > 0;
    }

    public boolean existsByMaterial(Long materialId) {
        return count("SELECT COUNT(p) FROM Pijama p JOIN p.materiais m WHERE m.id = ?1", materialId) > 0;
    }

}
