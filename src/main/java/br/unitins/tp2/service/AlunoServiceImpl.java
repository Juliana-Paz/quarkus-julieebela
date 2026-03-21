package br.unitins.tp2.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.AlunoDTO;               
import br.unitins.tp2.dto.AlunoResponseDTO;     
import br.unitins.tp2.dto.TelefoneDTO;    
import br.unitins.tp2.model.Aluno;
import br.unitins.tp2.model.Telefone;
import br.unitins.tp2.repository.AlunoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AlunoServiceImpl implements AlunoService {

    @Inject
    AlunoRepository alunoRepository;

    @Inject
    Validator validator;

    @Override
    public List<AlunoResponseDTO> getAll(int page, int pageSize) {
        List<Aluno> list = alunoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(AlunoResponseDTO::valueOf).toList();
    }

    @Override
    public AlunoResponseDTO findById(Long id) {
        Aluno aluno = alunoRepository.findById(id);
        if (aluno == null) throw new NotFoundException("Aluno não encontrado.");
        return AlunoResponseDTO.valueOf(aluno);
    }

    @Override
    public List<AlunoResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Aluno> list = alunoRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(AlunoResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return alunoRepository.count();
    }

    @Override
    public long countNome(String nome) {
        return alunoRepository.countNome(nome);
    }

    @Override
    @Transactional
    public AlunoResponseDTO create(AlunoDTO dto) throws ConstraintViolationException {
        validar(dto);
        Aluno entity = new Aluno();
        apply(dto, entity);
        alunoRepository.persist(entity);
        return AlunoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public AlunoResponseDTO update(Long id, AlunoDTO dto) throws ConstraintViolationException {
        validar(dto);
        Aluno entity = alunoRepository.findById(id);
        if (entity == null) throw new NotFoundException("Aluno não encontrado.");
        apply(dto, entity);
        return AlunoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = alunoRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Aluno não encontrado.");
    }

    private void validar(AlunoDTO dto) throws ConstraintViolationException {
        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    /** Copia/normaliza os campos do DTO para a entidade. */
    private void apply(AlunoDTO dto, Aluno entity) {
        // Texto comum: apenas trim
        entity.setNome(safeTrim(dto.nome()));
        entity.setSobrenome(safeTrim(dto.sobrenome()));
        entity.setEmail(safeTrim(dto.email()));
        entity.setDataNascimento(dto.dataNascimento());

        // Numéricos em String: somente dígitos
        entity.setCpf(normalizeDigits(dto.cpf()));

        // Telefones (ElementCollection com Embeddable)
        List<Telefone> tels = (dto.telefones() == null ? Collections.<TelefoneDTO>emptyList() : dto.telefones())
            .stream()
            .map(t -> {
                Telefone tel = new Telefone();
                tel.setCodigoArea(normalizeDigits(t.codigoArea())); // DDD
                tel.setNumero(normalizeDigits(t.numero()));         // número
                return tel;
            })
            .toList();

        entity.setTelefones(tels);
    }

    /** Remove espaços laterais, preservando null. */
    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    /** Mantém apenas dígitos (remove espaços, pontos, traços, parênteses etc.). */
    private String normalizeDigits(String s) {
        return (s == null) ? null : s.replaceAll("\\D", "");
    }
}
