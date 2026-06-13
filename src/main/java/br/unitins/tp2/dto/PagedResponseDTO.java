package br.unitins.tp2.dto;

import java.util.List;

public record PagedResponseDTO<T>(
    List<T> data,
    long total,
    int page,
    int size
) {}
