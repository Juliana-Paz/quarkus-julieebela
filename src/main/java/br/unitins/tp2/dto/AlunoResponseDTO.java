package br.unitins.tp2.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp2.model.Aluno;

public record AlunoResponseDTO(
    Long id,
    String nome,
    String sobrenome,
    LocalDate dataNascimento,
    String cpf,
    String email,
    List<TelefoneResponseDTO> telefones
    ) {

public static AlunoResponseDTO valueOf(Aluno aluno) {
    return new AlunoResponseDTO(
        aluno.getId(),
        aluno.getNome(),
        aluno.getSobrenome(),
        aluno.getDataNascimento(),
        aluno.getCpf(),
        aluno.getEmail(),
        aluno.getTelefones() != null
            ? aluno.getTelefones()
                   .stream()
                   .map(TelefoneResponseDTO::valueOf)
                   .toList()
            : List.of()
    );
}

}
