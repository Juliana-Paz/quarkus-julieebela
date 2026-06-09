package br.unitins.tp2.service;

import java.util.ArrayList;
import java.util.Set;

import br.unitins.tp2.dto.ClienteAlterarSenhaDTO;
import br.unitins.tp2.dto.ClienteDTO;
import br.unitins.tp2.dto.ClienteResponseDTO;
import br.unitins.tp2.dto.ClienteUpdateDTO;
import br.unitins.tp2.dto.EnderecoDTO;
import br.unitins.tp2.dto.TelefoneDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Cliente;
import br.unitins.tp2.model.Endereco;
import br.unitins.tp2.model.Perfil;
import br.unitins.tp2.model.Telefone;
import br.unitins.tp2.model.Usuario;
import br.unitins.tp2.repository.ClienteRepository;
import br.unitins.tp2.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO dto) {
        validar(dto);

        if (usuarioRepository.findByUsername(dto.username()) != null)
            throw ValidationException.of("username", "Username já está em uso.");

        if (clienteRepository.findByCpf(dto.cpf()) != null)
            throw ValidationException.of("cpf", "CPF já cadastrado.");

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setPerfil(Perfil.USER);

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setUsuario(usuario);

        if (dto.telefones() != null) {
            dto.telefones().forEach(t -> cliente.getTelefones().add(toTelefone(t)));
        }
        if (dto.enderecos() != null) {
            dto.enderecos().forEach(e -> cliente.getEnderecos().add(toEndereco(e)));
        }

        try {
            clienteRepository.persist(cliente);
            clienteRepository.flush();
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof org.hibernate.exception.ConstraintViolationException hcve) {
                    String constraintName = hcve.getConstraintName() != null ? hcve.getConstraintName() : "";
                    String sqlMsg = hcve.getSQLException() != null && hcve.getSQLException().getMessage() != null
                            ? hcve.getSQLException().getMessage() : "";
                    String combined = (constraintName + " " + sqlMsg).toLowerCase();
                    if (combined.contains("cliente_email_key")) {
                        throw ValidationException.of("email", "E-mail já cadastrado.");
                    }
                    if (combined.contains("cliente_cpf_key")) {
                        throw ValidationException.of("cpf", "CPF já cadastrado.");
                    }
                }
                cause = cause.getCause();
            }
            if (ex instanceof RuntimeException re) throw re;
            throw new RuntimeException(ex);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.getUsuario().setNome(dto.nome());

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findByUsername(String username) {
        Cliente cliente = clienteRepository.findByUsuarioUsername(username);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO adicionarEndereco(Long id, EnderecoDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        cliente.getEnderecos().add(toEndereco(dto));
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO atualizarEndereco(Long id, int index, EnderecoDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        if (index < 0 || index >= cliente.getEnderecos().size())
            throw ValidationException.of("index", "Índice de endereço inválido.");
        Endereco e = cliente.getEnderecos().get(index);
        e.setLogradouro(dto.logradouro());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCep(dto.cep());
        e.setMunicipio(dto.municipio());
        e.setEstado(dto.estado());
        e.setPrincipal(dto.principal());
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO removerEndereco(Long id, int index) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        if (index < 0 || index >= cliente.getEnderecos().size())
            throw ValidationException.of("index", "Índice de endereço inválido.");
        cliente.setEnderecos(new ArrayList<>(cliente.getEnderecos()));
        cliente.getEnderecos().remove(index);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO adicionarTelefone(Long id, TelefoneDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        cliente.getTelefones().add(toTelefone(dto));
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO removerTelefone(Long id, int index) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        if (index < 0 || index >= cliente.getTelefones().size())
            throw ValidationException.of("index", "Índice de telefone inválido.");
        cliente.setTelefones(new ArrayList<>(cliente.getTelefones()));
        cliente.getTelefones().remove(index);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void alterarSenha(Long id, ClienteAlterarSenhaDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");

        String hashAtual = hashService.getHashSenha(dto.senhaAtual());
        if (!hashAtual.equals(cliente.getUsuario().getSenha()))
            throw ValidationException.of("senhaAtual", "Senha atual incorreta.");

        cliente.getUsuario().setSenha(hashService.getHashSenha(dto.novaSenha()));
    }

    private void validar(ClienteDTO dto) {
        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private Telefone toTelefone(TelefoneDTO dto) {
        Telefone t = new Telefone();
        t.setCodigoArea(dto.codigoArea());
        t.setNumero(dto.numero());
        return t;
    }

    private Endereco toEndereco(EnderecoDTO dto) {
        Endereco e = new Endereco();
        e.setLogradouro(dto.logradouro());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCep(dto.cep());
        e.setMunicipio(dto.municipio());
        e.setEstado(dto.estado());
        e.setPrincipal(dto.principal());
        return e;
    }

}
