package br.unitins.tp2.repository;

import br.unitins.tp2.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public PanacheQuery<Pedido> findByClienteId(Long clienteId) {
        return find("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC", clienteId);
    }

}
