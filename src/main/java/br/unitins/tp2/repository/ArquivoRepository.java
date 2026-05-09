package br.unitins.tp2.repository;

import java.util.Optional;

import br.unitins.tp2.model.Arquivo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArquivoRepository implements PanacheRepository<Arquivo> {

    public Optional<Arquivo> findByFid(String fid) {
        return find("fid", fid).firstResultOptional();
    }
}