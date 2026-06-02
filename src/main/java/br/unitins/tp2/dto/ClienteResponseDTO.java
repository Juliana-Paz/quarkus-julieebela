package br.unitins.tp2.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp2.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    LocalDate dataNascimento,
    List<TelefoneResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos,
    UsuarioResponseDTO usuario
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getEmail(),
            cliente.getDataNascimento(),
            cliente.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
            cliente.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
            UsuarioResponseDTO.valueOf(cliente.getUsuario())
        );
    }
}
