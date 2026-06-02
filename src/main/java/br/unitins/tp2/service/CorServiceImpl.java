package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.CorDTO;
import br.unitins.tp2.dto.CorResponseDTO;
import br.unitins.tp2.model.Cor;
import br.unitins.tp2.repository.CorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CorServiceImpl implements CorService {

    @Inject
    CorRepository corRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public CorResponseDTO create(CorDTO dto) {
        validar(dto);
        Cor entity = new Cor();
        apply(dto, entity);
        corRepository.persist(entity);
        return CorResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CorResponseDTO update(Long id, CorDTO dto) {
        validar(dto);
        Cor entity = corRepository.findById(id);
        if (entity == null) throw new NotFoundException("Cor não encontrada.");
        apply(dto, entity);
        return CorResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = corRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Cor não encontrada.");
    }

    @Override
    public CorResponseDTO findById(Long id) {
        Cor entity = corRepository.findById(id);
        if (entity == null) throw new NotFoundException("Cor não encontrada.");
        return CorResponseDTO.valueOf(entity);
    }

    @Override
    public List<CorResponseDTO> findAll(int page, int pageSize) {
        return corRepository.findAll().page(page, pageSize).list()
                .stream().map(CorResponseDTO::valueOf).toList();
    }

    @Override
    public List<CorResponseDTO> findByNome(String nome, int page, int pageSize) {
        return corRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(CorResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return corRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return corRepository.countByNome(nome);
    }

    private void validar(CorDTO dto) {
        Set<ConstraintViolation<CorDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(CorDTO dto, Cor entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setHexadecimal(safeTrim(dto.hexadecimal()));
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
