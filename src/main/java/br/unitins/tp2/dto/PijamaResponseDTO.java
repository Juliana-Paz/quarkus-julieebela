package br.unitins.tp2.dto;

import java.util.List;

import br.unitins.tp2.dto.pijama.PijamaVarianteResponseDTO;
import br.unitins.tp2.model.Pijama;
import br.unitins.tp2.model.SexoPijama;

public record PijamaResponseDTO(
    Long id,
    String nome,
    String descricao,
    Double preco,
    String modelo,
    Boolean ativo,
    SexoPijama sexo,
    CategoriaResponseDTO categoria,
    MarcaResponseDTO marca,
    EstampaResponseDTO estampa,
    List<MaterialResponseDTO> materiais,
    List<ArquivoResponseDTO> imagens,
    List<PijamaVarianteResponseDTO> variantes
) {
    public static PijamaResponseDTO valueOf(Pijama p) {
        return new PijamaResponseDTO(
            p.getId(),
            p.getNome(),
            p.getDescricao(),
            p.getPreco(),
            p.getModelo(),
            p.getAtivo(),
            p.getSexo(),
            p.getCategoria() != null ? CategoriaResponseDTO.valueOf(p.getCategoria()) : null,
            p.getMarca() != null ? MarcaResponseDTO.valueOf(p.getMarca()) : null,
            p.getEstampa() != null ? EstampaResponseDTO.valueOf(p.getEstampa()) : null,
            p.getMateriais() != null
                ? p.getMateriais().stream().map(MaterialResponseDTO::valueOf).toList()
                : List.of(),
            p.getImagens() != null
                ? p.getImagens().stream().map(ArquivoResponseDTO::valueOf).toList()
                : List.of(),
            p.getVariantes() != null
                ? p.getVariantes().stream().map(PijamaVarianteResponseDTO::valueOf).toList()
                : List.of()
        );
    }
}
