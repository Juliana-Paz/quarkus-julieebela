package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.UsuarioDTO;
import br.unitins.tp2.dto.UsuarioResponseDTO;
import br.unitins.tp2.dto.usuario.AdmUpdateDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Perfil;
import br.unitins.tp2.model.Usuario;
import br.unitins.tp2.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    HashService hashService;

    @Override
    @Transactional
    public UsuarioResponseDTO insert(UsuarioDTO dto) {
        if (repository.findByUsername(dto.username()) != null) {
            throw  ValidationException.of("username", "O username informado já existe, informe outro username.");
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setUsername(dto.username());
        novoUsuario.setSenha(dto.senha());
        novoUsuario.setPerfil(Perfil.valueOf(dto.idPerfil()));

        repository.persist(novoUsuario);

        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioDTO dto, Long id) {
        return null;

    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
         return null;
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
             return null;
    }

    @Override
    public List<UsuarioResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha) {
        Usuario usuario = repository.findByUsernameAndSenha(login, senha);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        Usuario usuario = repository.findByUsername(username);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateAdm(String usernameLogado, AdmUpdateDTO dto) {
        Usuario usuario = repository.findByUsername(usernameLogado);
        if (usuario == null) {
            throw ValidationException.of("usuario", "Usuário não encontrado.");
        }

        // Verifica conflito de username com outro usuário
        Usuario existente = repository.findByUsername(dto.username());
        if (existente != null && !existente.getId().equals(usuario.getId())) {
            throw ValidationException.of("username", "Este username já está em uso.");
        }

        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());

        if (dto.novaSenha() != null && !dto.novaSenha().isBlank()) {
            if (dto.senhaAtual() == null || dto.senhaAtual().isBlank()) {
                throw ValidationException.of("senhaAtual", "Informe a senha atual para alterá-la.");
            }
            if (!hashService.getHashSenha(dto.senhaAtual()).equals(usuario.getSenha())) {
                throw ValidationException.of("senhaAtual", "Senha atual incorreta.");
            }
            usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
        }

        return UsuarioResponseDTO.valueOf(usuario);
    }

}
