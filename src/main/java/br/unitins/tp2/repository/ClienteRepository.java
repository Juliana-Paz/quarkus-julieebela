package br.unitins.tp2.repository;

import br.unitins.tp2.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findByUsuarioUsername(String username) {
        if (username == null)
            return null;
        return find("usuario.username = ?1", username).firstResult();
    }

    public Cliente findByCpf(String cpf) {
        if (cpf == null)
            return null;
        return find("cpf = ?1", cpf).firstResult();
    }

}
