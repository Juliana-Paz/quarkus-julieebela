package br.unitins.tp2.dto;

import java.util.List;

import br.unitins.tp2.model.Pijama;
import br.unitins.tp2.model.SexoPijama;
import br.unitins.tp2.model.Tamanho;

public record PijamaResponseDTO(
    Long id,
    String nome,
    String descricao,
    Double preco,
    String modelo,
    Integer estoque,
    Boolean ativo,
    Tamanho tamanho,
    SexoPijama sexo,
    CategoriaResponseDTO categoria,
    MarcaResponseDTO marca,
    EstampaResponseDTO estampa,
    List<CorResponseDTO> cores,
    List<MaterialResponseDTO> materiais,
    List<ArquivoResponseDTO> imagens
) {
    public static PijamaResponseDTO valueOf(Pijama pijama) {
        return new PijamaResponseDTO(
            pijama.getId(),
            pijama.getNome(),
            pijama.getDescricao(),
            pijama.getPreco(),
            pijama.getModelo(),
            pijama.getEstoque(),
            pijama.getAtivo(),
            pijama.getTamanho(),
            pijama.getSexo(),
            CategoriaResponseDTO.valueOf(pijama.getCategoria()),
            MarcaResponseDTO.valueOf(pijama.getMarca()),
            pijama.getEstampa() != null ? EstampaResponseDTO.valueOf(pijama.getEstampa()) : null,
            pijama.getCores().stream().map(CorResponseDTO::valueOf).toList(),
            pijama.getMateriais().stream().map(MaterialResponseDTO::valueOf).toList(),
            pijama.getImagens().stream().map(ArquivoResponseDTO::valueOf).toList()
        );
    }
}
