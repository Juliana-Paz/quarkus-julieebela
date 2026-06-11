package br.unitins.tp2.service;

import br.unitins.tp2.dto.recuperacao.RedefinirSenhaDTO;
import br.unitins.tp2.dto.recuperacao.SolicitarRecuperacaoDTO;
import br.unitins.tp2.dto.recuperacao.TokenTemporarioDTO;
import br.unitins.tp2.dto.recuperacao.VerificarCodigoDTO;
import br.unitins.tp2.exception.NotFoundException;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Cliente;
import br.unitins.tp2.model.TokenRecuperacaoSenha;
import br.unitins.tp2.repository.ClienteRepository;
import br.unitins.tp2.repository.TokenRecuperacaoSenhaRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class RecuperacaoSenhaServiceImpl implements RecuperacaoSenhaService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TokenRecuperacaoSenhaRepository tokenRepository;

    @Inject
    HashService hashService;

    @Inject
    Mailer mailer;

    // token temporário → usuarioId (válido por 15 minutos após verificação do código)
    private final ConcurrentHashMap<String, Long> tokensTemporarios = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public void solicitarRecuperacao(SolicitarRecuperacaoDTO dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.email());
        if (cliente == null) {
            throw new NotFoundException("Nenhuma conta encontrada com este e-mail.");
        }

        tokenRepository.invalidarTokensAnteriores(cliente.getUsuario().getId());

        String codigo = String.format("%06d", new Random().nextInt(1_000_000));

        TokenRecuperacaoSenha token = new TokenRecuperacaoSenha();
        token.setUsuario(cliente.getUsuario());
        token.setCodigo(codigo);
        token.setExpiracao(LocalDateTime.now().plusMinutes(15));
        token.setUsado(false);
        tokenRepository.persist(token);

        System.out.println("============================================");
        System.out.println(">>> CÓDIGO DE RECUPERAÇÃO DE SENHA");
        System.out.println(">>> Email: " + dto.email());
        System.out.println(">>> Código: " + codigo);
        System.out.println(">>> Expira em: " + token.getExpiracao());
        System.out.println("============================================");

        mailer.send(Mail.withText(
                dto.email(),
                "Julie e Bela — Recuperação de senha",
                "Seu código de recuperação é: " + codigo + "\n\nEste código expira em 15 minutos.\n\nSe você não solicitou a recuperação, ignore este e-mail."
        ));
    }

    @Override
    public TokenTemporarioDTO verificarCodigo(VerificarCodigoDTO dto) {
        TokenRecuperacaoSenha token = tokenRepository.findByCodigo(dto.codigo())
                .orElseThrow(() -> ValidationException.of("codigo", "Código inválido ou expirado."));

        marcarComoUsado(token.getId());

        String tokenTemporario = UUID.randomUUID().toString();
        tokensTemporarios.put(tokenTemporario, token.getUsuario().getId());

        return new TokenTemporarioDTO(tokenTemporario);
    }

    @Transactional
    void marcarComoUsado(Long tokenId) {
        TokenRecuperacaoSenha token = tokenRepository.findById(tokenId);
        if (token != null) {
            token.setUsado(true);
        }
    }

    @Override
    @Transactional
    public void redefinirSenha(RedefinirSenhaDTO dto) {
        Long usuarioId = tokensTemporarios.remove(dto.tokenTemporario());
        if (usuarioId == null) {
            throw ValidationException.of("tokenTemporario", "Sessão de recuperação inválida ou expirada. Solicite um novo código.");
        }

        Cliente cliente = clienteRepository.find("usuario.id = ?1", usuarioId).firstResult();
        if (cliente == null) {
            throw ValidationException.of("tokenTemporario", "Usuário não encontrado.");
        }

        cliente.getUsuario().setSenha(hashService.getHashSenha(dto.novaSenha()));
    }
}
