package br.unitins.tp2.dto;

public record PlanoDTO(
    String nome,
    Integer maxAlunos,
    Integer maxProfessores,
    Double precoMensal,
    Double descontoAnual
) { }