package br.unitins.tp2.dto;

import br.unitins.tp2.model.Arquivo;

public record ArquivoResponseDTO(
    Long id,
    String fid,
    String nomeOriginal,
    String mimeType,
    Long tamanhoBytes,
    String sha256,
    Integer ordem,
    Boolean capa
) {
    public static ArquivoResponseDTO valueOf(Arquivo arquivo) {
        return new ArquivoResponseDTO(
            arquivo.getId(),
            arquivo.getFid(),
            arquivo.getNomeOriginal(),
            arquivo.getMimeType(),
            arquivo.getTamanhoBytes(),
            arquivo.getSha256(),
            arquivo.getOrdem() != null ? arquivo.getOrdem() : 0,
            arquivo.getCapa() != null ? arquivo.getCapa() : false
        );
    }
}