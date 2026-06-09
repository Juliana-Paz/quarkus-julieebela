package br.unitins.tp2.repository;

import br.unitins.tp2.model.PijamaVariante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PijamaVarianteRepository implements PanacheRepository<PijamaVariante> {
}
