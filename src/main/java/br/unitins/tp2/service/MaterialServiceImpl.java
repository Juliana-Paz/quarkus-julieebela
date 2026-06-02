package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.MaterialDTO;
import br.unitins.tp2.dto.MaterialResponseDTO;
import br.unitins.tp2.model.Material;
import br.unitins.tp2.repository.MaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MaterialServiceImpl implements MaterialService {

    @Inject
    MaterialRepository materialRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public MaterialResponseDTO create(MaterialDTO dto) {
        validar(dto);
        Material entity = new Material();
        apply(dto, entity);
        materialRepository.persist(entity);
        return MaterialResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public MaterialResponseDTO update(Long id, MaterialDTO dto) {
        validar(dto);
        Material entity = materialRepository.findById(id);
        if (entity == null) throw new NotFoundException("Material não encontrado.");
        apply(dto, entity);
        return MaterialResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = materialRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Material não encontrado.");
    }

    @Override
    public MaterialResponseDTO findById(Long id) {
        Material entity = materialRepository.findById(id);
        if (entity == null) throw new NotFoundException("Material não encontrado.");
        return MaterialResponseDTO.valueOf(entity);
    }

    @Override
    public List<MaterialResponseDTO> findAll(int page, int pageSize) {
        return materialRepository.findAll().page(page, pageSize).list()
                .stream().map(MaterialResponseDTO::valueOf).toList();
    }

    @Override
    public List<MaterialResponseDTO> findByNome(String nome, int page, int pageSize) {
        return materialRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(MaterialResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return materialRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return materialRepository.countByNome(nome);
    }

    private void validar(MaterialDTO dto) {
        Set<ConstraintViolation<MaterialDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(MaterialDTO dto, Material entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setDescricao(safeTrim(dto.descricao()));
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
