package br.unitins.tp2.service;

import java.util.List;

import br.unitins.tp2.dto.EstadoDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Estado;
import br.unitins.tp2.model.Regiao;
import br.unitins.tp2.repository.EstadoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository estadoRepository;

    @Override
    @Transactional
    public Estado create(EstadoDTO estado) {
        validarDados(estado, null);
        Estado novoEstado = new Estado();
        novoEstado.setNome(estado.getNome());
        novoEstado.setSigla(estado.getSigla());

        // selecionar uma regiao
        novoEstado.setRegiao(Regiao.valueOf(estado.getIdRegiao()));

        // realizando inclusao
        estadoRepository.persist(novoEstado);

        return novoEstado;
    }

    private void validarDados(EstadoDTO dto, Long id) {
        Estado estado = estadoRepository.findBySiglaExceptId(dto.getSigla(), id);
        if (estado != null)
           throw ValidationException.of("sigla", "Já existe um estado cadastrado com essa sigla.");
    }

    @Override
    @Transactional
    public void update(long id, EstadoDTO estado) {
        validarDados(estado, id);
        Estado edicaoEstado = estadoRepository.findById(id);

        edicaoEstado.setNome(estado.getNome());
        edicaoEstado.setSigla(estado.getSigla());

        // selecionar uma regiao
        edicaoEstado.setRegiao(Regiao.valueOf(estado.getIdRegiao()));
    }

    @Override
    @Transactional
    public void delete(long id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public Estado findById(long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public Estado findBySigla(String sigla) {
        return estadoRepository.findBySigla(sigla);
    }

    @Override
    public List<Estado> findAll(Integer page, Integer pageSize) {
        PanacheQuery<Estado> query = null;

        query = estadoRepository.findAll().page(page, pageSize);

        return query.list();
    }

    @Override
    public List<Estado> findByNome(String nome, Integer page, Integer pageSize) {
        return estadoRepository.findByNome(nome).page(page, pageSize).list();
    }

    public List<Estado> findByNome(String nome) {
        return estadoRepository.findByNome(nome).list();
    }

    @Override
    public long count() {
        return estadoRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return estadoRepository.findByNome(nome).count();
    }

}
