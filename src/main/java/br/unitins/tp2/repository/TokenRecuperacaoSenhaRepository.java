package br.unitins.tp2.repository;

import br.unitins.tp2.model.TokenRecuperacaoSenha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class TokenRecuperacaoSenhaRepository implements PanacheRepository<TokenRecuperacaoSenha> {

    public Optional<TokenRecuperacaoSenha> findByCodigo(String codigo) {
        return find("codigo = ?1 and usado = false and expiracao > ?2",
                codigo, LocalDateTime.now()).firstResultOptional();
    }

    public void invalidarTokensAnteriores(Long usuarioId) {
        update("usado = ?1 where usuario.id = ?2 and usado = false", true, usuarioId);
    }
}
