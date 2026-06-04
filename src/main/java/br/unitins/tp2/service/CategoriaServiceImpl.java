package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.CategoriaDTO;
import br.unitins.tp2.dto.CategoriaResponseDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Categoria;
import br.unitins.tp2.repository.CategoriaRepository;
import br.unitins.tp2.repository.PijamaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    PijamaRepository pijamaRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaDTO dto) {
        validar(dto);
        Categoria entity = new Categoria();
        apply(dto, entity);
        categoriaRepository.persist(entity);
        return CategoriaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaDTO dto) {
        validar(dto);
        Categoria entity = categoriaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Categoria não encontrada.");
        apply(dto, entity);
        return CategoriaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (categoriaRepository.findById(id) == null) throw new NotFoundException("Categoria não encontrada.");
        if (pijamaRepository.countByCategoria(id) > 0)
            throw ValidationException.of("categoria", "Não é possível excluir a categoria pois existem pijamas vinculados a ela.");
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        Categoria entity = categoriaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Categoria não encontrada.");
        return CategoriaResponseDTO.valueOf(entity);
    }

    @Override
    public List<CategoriaResponseDTO> findAll(int page, int pageSize) {
        return categoriaRepository.findAll().page(page, pageSize).list()
                .stream().map(CategoriaResponseDTO::valueOf).toList();
    }

    @Override
    public List<CategoriaResponseDTO> findByNome(String nome, int page, int pageSize) {
        return categoriaRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(CategoriaResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return categoriaRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return categoriaRepository.countByNome(nome);
    }

    private void validar(CategoriaDTO dto) {
        Set<ConstraintViolation<CategoriaDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(CategoriaDTO dto, Categoria entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setDescricao(safeTrim(dto.descricao()));
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
