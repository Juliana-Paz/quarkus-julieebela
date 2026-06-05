package br.unitins.tp2.repository;

import java.util.List;

import br.unitins.tp2.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario>{
    public List<Usuario> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }

    public Usuario findByUsername(String login) {
        return find("username = ?1 ", login).firstResult();
    }

    public Usuario findByUsernameAndSenha(String username, String senha){
        if (username == null || senha == null)
            return null;

        String input = username.toUpperCase();

        return getEntityManager()
                .createQuery(
                    "SELECT u FROM Usuario u WHERE u.senha = :senha AND " +
                    "(UPPER(u.username) = :input OR " +
                    " EXISTS (SELECT c FROM Cliente c WHERE c.usuario = u AND UPPER(c.email) = :input))",
                    Usuario.class)
                .setParameter("senha", senha)
                .setParameter("input", input)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
