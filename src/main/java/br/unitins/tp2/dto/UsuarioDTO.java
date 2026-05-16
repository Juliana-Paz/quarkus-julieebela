package br.unitins.tp2.dto;

public record UsuarioDTO (
    String nome,
    String username,
    String senha,
    int idPerfil
) {

}
