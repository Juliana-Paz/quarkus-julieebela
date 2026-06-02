package br.unitins.tp2.repository;

import br.unitins.tp2.model.CupomDesconto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomRepository implements PanacheRepository<CupomDesconto> {

    @Override
    public PanacheQuery<CupomDesconto> findAll() {
        return find("SELECT c FROM CupomDesconto c ORDER BY c.codigo");
    }

    public CupomDesconto findByCodigo(String codigo) {
        if (codigo == null)
            return null;
        return find("UPPER(codigo) = ?1", codigo.toUpperCase()).firstResult();
    }

    public PanacheQuery<CupomDesconto> findAtivos() {
        return find("SELECT c FROM CupomDesconto c WHERE c.ativo = true ORDER BY c.dataInicio DESC");
    }

}
