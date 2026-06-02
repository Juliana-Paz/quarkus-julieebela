package br.unitins.tp2.repository;

import java.util.List;

import br.unitins.tp2.model.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {

    public List<ItemPedido> findByPedidoId(Long pedidoId) {
        return find("pedido.id = ?1", pedidoId).list();
    }

}
