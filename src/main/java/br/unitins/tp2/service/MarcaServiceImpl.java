package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.MarcaDTO;
import br.unitins.tp2.dto.MarcaResponseDTO;
import br.unitins.tp2.model.Marca;
import br.unitins.tp2.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public MarcaResponseDTO create(MarcaDTO dto) {
        validar(dto);
        Marca entity = new Marca();
        apply(dto, entity);
        marcaRepository.persist(entity);
        return MarcaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public MarcaResponseDTO update(Long id, MarcaDTO dto) {
        validar(dto);
        Marca entity = marcaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Marca não encontrada.");
        apply(dto, entity);
        return MarcaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = marcaRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Marca não encontrada.");
    }

    @Override
    public MarcaResponseDTO findById(Long id) {
        Marca entity = marcaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Marca não encontrada.");
        return MarcaResponseDTO.valueOf(entity);
    }

    @Override
    public List<MarcaResponseDTO> findAll(int page, int pageSize) {
        return marcaRepository.findAll().page(page, pageSize).list()
                .stream().map(MarcaResponseDTO::valueOf).toList();
    }

    @Override
    public List<MarcaResponseDTO> findByNome(String nome, int page, int pageSize) {
        return marcaRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(MarcaResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return marcaRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return marcaRepository.countByNome(nome);
    }

    private void validar(MarcaDTO dto) {
        Set<ConstraintViolation<MarcaDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(MarcaDTO dto, Marca entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setDescricao(safeTrim(dto.descricao()));
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
