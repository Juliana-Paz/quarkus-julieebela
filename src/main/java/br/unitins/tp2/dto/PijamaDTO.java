package br.unitins.tp2.dto;

import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public record PijamaDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    String nome,

    @Length(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    String descricao,

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    Double preco,

    @Length(max = 100, message = "O modelo deve ter no máximo 100 caracteres.")
    String modelo,

    @NotNull(message = "O estoque é obrigatório.")
    @Min(value = 0, message = "O estoque não pode ser negativo.")
    Integer estoque,

    @NotNull(message = "O campo ativo é obrigatório.")
    Boolean ativo,

    @NotNull(message = "O tamanho é obrigatório.")
    Integer idTamanho,

    @NotNull(message = "O sexo é obrigatório.")
    Integer idSexo,

    @NotNull(message = "A categoria é obrigatória.")
    Long idCategoria,

    @NotNull(message = "A marca é obrigatória.")
    Long idMarca,

    Long idEstampa,

    List<Long> idsCores,

    List<Long> idsMateriais

) {}
