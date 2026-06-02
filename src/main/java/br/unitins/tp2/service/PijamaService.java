package br.unitins.tp2.service;

import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.tp2.dto.PijamaDTO;
import br.unitins.tp2.dto.PijamaResponseDTO;

public interface PijamaService {

    PijamaResponseDTO create(PijamaDTO dto);

    PijamaResponseDTO update(Long id, PijamaDTO dto);

    void delete(Long id);

    PijamaResponseDTO findById(Long id);

    List<PijamaResponseDTO> findAll(int page, int pageSize);

    List<PijamaResponseDTO> findByNome(String nome, int page, int pageSize);

    List<PijamaResponseDTO> findByCategoria(Long idCategoria, int page, int pageSize);

    List<PijamaResponseDTO> findByMarca(Long idMarca, int page, int pageSize);

    long count();

    long countByNome(String nome);

    void adicionarImagem(Long id, FileUpload file) throws IOException;

    ArquivoDownload downloadImagem(String fid);

    void removerImagem(String fid);

}
