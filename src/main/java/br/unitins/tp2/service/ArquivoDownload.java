package br.unitins.tp2.service;

public record ArquivoDownload(
    byte[] content,
    String contentType,
    String fileName
) {
}