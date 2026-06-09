package br.unitins.tp2.dto.pijama;

import br.unitins.tp2.dto.CorResponseDTO;
import br.unitins.tp2.model.PijamaVariante;

public record PijamaVarianteResponseDTO(
    Long id,
    String tamanhoId,
    String tamanhoNome,
    CorResponseDTO cor,
    Integer estoque
) {
    public static PijamaVarianteResponseDTO valueOf(PijamaVariante v) {
        return new PijamaVarianteResponseDTO(
            v.getId(),
            v.getTamanho().name(),
            v.getTamanho().getNome(),
            v.getCor() != null ? CorResponseDTO.valueOf(v.getCor()) : null,
            v.getEstoque()
        );
    }
}
