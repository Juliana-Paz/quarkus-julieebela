package br.unitins.tp2.service;

import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.PlanoDTO;
import br.unitins.tp2.dto.PlanoResponseDTO;
import br.unitins.tp2.model.Plano;
import br.unitins.tp2.repository.PlanoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PlanoServiceImpl implements PlanoService {

    @Inject
    PlanoRepository planoRepository;

    @Inject
    Validator validator;

    @Override
    public List<PlanoResponseDTO> getAll(int page, int pageSize) {
        List<Plano> list = planoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(PlanoResponseDTO::valueOf).toList();
    }

    @Override
    public PlanoResponseDTO findById(Long id) {
        Plano plano = planoRepository.findById(id);
        if (plano == null) throw new NotFoundException("Plano não encontrado.");
        return PlanoResponseDTO.valueOf(plano);
    }

    @Override
    public List<PlanoResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Plano> list = planoRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(PlanoResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return planoRepository.count();
    }

    @Override
    public long countNome(String nome) {
        return planoRepository.countNome(nome);
    }

    @Override
    @Transactional
    public PlanoResponseDTO create(PlanoDTO dto) throws ConstraintViolationException {
        validar(dto);
        Plano entity = new Plano();
        apply(dto, entity);
        planoRepository.persist(entity);
        return PlanoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public PlanoResponseDTO update(Long id, PlanoDTO dto) throws ConstraintViolationException {
        validar(dto);
        Plano entity = planoRepository.findById(id);
        if (entity == null) throw new NotFoundException("Plano não encontrado.");
        apply(dto, entity);
        return PlanoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = planoRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Plano não encontrado.");
    }

    private void validar(PlanoDTO dto) throws ConstraintViolationException {
        Set<ConstraintViolation<PlanoDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    /** Copia/normaliza os campos do DTO para a entidade. */
    private void apply(PlanoDTO dto, Plano entity) {
        entity.setNome(safeTrim(dto.nome()));
        entity.setMaxAlunos(dto.maxAlunos());
        entity.setMaxProfessores(dto.maxProfessores());
        entity.setPrecoMensal(dto.precoMensal());
        entity.setDescontoAnual(dto.descontoAnual());
    }

    /** Remove espaços laterais, preservando null. */
    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
