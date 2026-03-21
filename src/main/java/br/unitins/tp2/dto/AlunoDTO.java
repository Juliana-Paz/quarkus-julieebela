package br.unitins.tp2.dto;

import java.time.LocalDate;
import java.util.List;

public record AlunoDTO(
    String nome,
    String sobrenome,
    LocalDate dataNascimento,
    String cpf,
    String email,
    List<TelefoneDTO> telefones
) { }