package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.EstampaDTO;
import br.unitins.tp2.dto.EstampaResponseDTO;
import br.unitins.tp2.model.Estampa;
import br.unitins.tp2.repository.EstampaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstampaServiceImpl implements EstampaService {

    @Inject
    EstampaRepository estampaRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public EstampaResponseDTO create(EstampaDTO dto) {
        validar(dto);
        Estampa entity = new Estampa();
        apply(dto, entity);
        estampaRepository.persist(entity);
        return EstampaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public EstampaResponseDTO update(Long id, EstampaDTO dto) {
        validar(dto);
        Estampa entity = estampaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Estampa não encontrada.");
        apply(dto, entity);
        return EstampaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = estampaRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Estampa não encontrada.");
    }

    @Override
    public EstampaResponseDTO findById(Long id) {
        Estampa entity = estampaRepository.findById(id);
        if (entity == null) throw new NotFoundException("Estampa não encontrada.");
        return EstampaResponseDTO.valueOf(entity);
    }

    @Override
    public List<EstampaResponseDTO> findAll(int page, int pageSize) {
        return estampaRepository.findAll().page(page, pageSize).list()
                .stream().map(EstampaResponseDTO::valueOf).toList();
    }

    @Override
    public List<EstampaResponseDTO> findByNome(String nome, int page, int pageSize) {
        return estampaRepository.findByNome(nome).page(page, pageSize).list()
                .stream().map(EstampaResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return estampaRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return estampaRepository.countByNome(nome);
    }

    private void validar(EstampaDTO dto) {
        Set<ConstraintViolation<EstampaDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(EstampaDTO dto, Estampa entity) {
        entity.setNome(safeTrim(dto.nome()));
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
