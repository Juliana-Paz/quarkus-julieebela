package br.unitins.tp2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import br.unitins.tp2.dto.CupomDTO;
import br.unitins.tp2.dto.CupomResponseDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.CupomDesconto;
import br.unitins.tp2.repository.CupomRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CupomServiceImpl implements CupomService {

    @Inject
    CupomRepository cupomRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public CupomResponseDTO create(CupomDTO dto) {
        validar(dto);
        validarCodigoDuplicado(dto.codigo(), null);
        CupomDesconto entity = new CupomDesconto();
        apply(dto, entity);
        cupomRepository.persist(entity);
        return CupomResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CupomResponseDTO update(Long id, CupomDTO dto) {
        validar(dto);
        CupomDesconto entity = cupomRepository.findById(id);
        if (entity == null) throw new NotFoundException("Cupom não encontrado.");
        validarCodigoDuplicado(dto.codigo(), id);
        apply(dto, entity);
        return CupomResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean removed = cupomRepository.deleteById(id);
        if (!removed) throw new NotFoundException("Cupom não encontrado.");
    }

    @Override
    public CupomResponseDTO findById(Long id) {
        CupomDesconto entity = cupomRepository.findById(id);
        if (entity == null) throw new NotFoundException("Cupom não encontrado.");
        return CupomResponseDTO.valueOf(entity);
    }

    @Override
    public List<CupomResponseDTO> findAll(int page, int pageSize) {
        return cupomRepository.findAll().page(page, pageSize).list()
                .stream().map(CupomResponseDTO::valueOf).toList();
    }

    @Override
    public CupomResponseDTO findByCodigo(String codigo) {
        CupomDesconto entity = cupomRepository.findByCodigo(codigo);
        if (entity == null) throw new NotFoundException("Cupom não encontrado.");
        return CupomResponseDTO.valueOf(entity);
    }

    @Override
    public List<CupomResponseDTO> findAtivos(int page, int pageSize) {
        return cupomRepository.findAtivos().page(page, pageSize).list()
                .stream().map(CupomResponseDTO::valueOf).toList();
    }

    @Override
    public CupomResponseDTO validarCupom(String codigo) {
        CupomDesconto cupom = cupomRepository.findByCodigo(codigo);
        if (cupom == null)
            throw ValidationException.of("codigo", "Cupom não encontrado.");
        if (!cupom.getAtivo())
            throw ValidationException.of("codigo", "Cupom inativo.");
        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(cupom.getDataInicio()))
            throw ValidationException.of("codigo", "Cupom ainda não está vigente.");
        if (hoje.isAfter(cupom.getDataFim()))
            throw ValidationException.of("codigo", "Cupom expirado.");
        return CupomResponseDTO.valueOf(cupom);
    }

    @Override
    public long count() {
        return cupomRepository.count();
    }

    private void validarCodigoDuplicado(String codigo, Long idAtual) {
        CupomDesconto existente = cupomRepository.findByCodigo(codigo);
        if (existente != null && !existente.getId().equals(idAtual))
            throw ValidationException.of("codigo", "Já existe um cupom cadastrado com esse código.");
    }

    private void validar(CupomDTO dto) {
        Set<ConstraintViolation<CupomDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    private void apply(CupomDTO dto, CupomDesconto entity) {
        entity.setCodigo(safeTrim(dto.codigo()).toUpperCase());
        entity.setDescricao(safeTrim(dto.descricao()));
        entity.setValorDesconto(dto.valorDesconto());
        entity.setPercentual(dto.percentual());
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setAtivo(dto.ativo());
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}
