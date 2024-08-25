package com.simplesdental.teste.persistence.repositories.impl;

import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.persistence.entities.ContatoEntity;
import com.simplesdental.teste.persistence.jpa.ContatoRepositoryJPA;
import com.simplesdental.teste.persistence.jpa.ProfissionalRepositoryJPA;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.persistence.utils.ModelsPersistenceUtils;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.simplesdental.teste.persistence.specifications.ContatoSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;


@Component
public class ContatoRepositoryImpl implements ContatoRepository {

    private final ContatoRepositoryJPA contatoRepositoryJPA;
    private final ProfissionalRepositoryJPA profissionalRepositoryJPA;
    private final ModelsPersistenceUtils persistenceUtils;

    public ContatoRepositoryImpl(ContatoRepositoryJPA contatoRepositoryJPA, ProfissionalRepositoryJPA profissionalRepositoryJPA, ModelsPersistenceUtils persistenceUtils) {
        this.contatoRepositoryJPA = contatoRepositoryJPA;
        this.profissionalRepositoryJPA = profissionalRepositoryJPA;
        this.persistenceUtils = persistenceUtils;
    }

    @Override
    public Contato salvarContato(Contato contato) {
        var entity = persistenceUtils.toEntity(contato);
        entity.setProfissional(profissionalRepositoryJPA.getReferenceById(contato.getIdProfissional()));

        if (entity.getId() == null) {
            entity.setCreatedDate(LocalDate.now());
        }

        return persistenceUtils.toDomain(contatoRepositoryJPA.save(entity));
    }

    @Override
    public Optional<Contato> findById(UUID id) {
        Optional<ContatoEntity> optionalEntity = contatoRepositoryJPA.findById(id);
        return optionalEntity.map(persistenceUtils::toDomain);

    }

    @Override
    public void removerContato(UUID id) {
        contatoRepositoryJPA.deleteById(id);
    }

    @Override
    public List<Contato> filtrarContatos(String q) {
        return StringUtils.isBlank(q) ?
                listarTodos()
                : filtrarContatos(
                where(
                        isIdEqualsTo(q))
                        .or(isNomeContains(q))
                        .or(isContatoContains(q))
                        .or(isCreatedDateEqualsTo(q))
                        .or(isIdProfissionalEqualsTo(q))
        );
    }

    public List<Contato> filtrarContatos(Specification<ContatoEntity> contatoSpecification) {
        return contatoRepositoryJPA
                .findAll(contatoSpecification).stream()
                .map(persistenceUtils::toDomain)
                .toList();
    }

    private List<Contato> listarTodos() {
        return contatoRepositoryJPA.findAll().stream().map(persistenceUtils::toDomain).toList();
    }

}
