package br.unitins.tp2.dto;

import br.unitins.tp2.model.Endereco;

public record EnderecoResponseDTO(
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep,
    String municipio,
    String estado,
    Boolean principal
) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getMunicipio(),
            endereco.getEstado(),
            endereco.getPrincipal()
        );
    }
}
