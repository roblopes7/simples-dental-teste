package com.simplesdental.teste.persistence.repositories.impl;

import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import com.simplesdental.teste.persistence.jpa.ProfissionalRepositoryJPA;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import com.simplesdental.teste.persistence.utils.ModelsPersistenceUtils;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.simplesdental.teste.persistence.specifications.ProfissionalSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ProfissionalRepositoryImpl implements ProfissionalRepository {

    private final ProfissionalRepositoryJPA profissionalRepositoryJPA;
    private final ModelsPersistenceUtils persistenceUtils;

    public ProfissionalRepositoryImpl(ProfissionalRepositoryJPA profissionalRepositoryJPA, ModelsPersistenceUtils persistenceUtils) {
        this.profissionalRepositoryJPA = profissionalRepositoryJPA;
        this.persistenceUtils = persistenceUtils;
    }

    @Override
    public Profissional salvarProfissional(Profissional profissional) {
        var entity = persistenceUtils.toEntity(profissional);

        if (entity.getId() == null) {
            entity.setCreatedDate(LocalDate.now());
        }

        return persistenceUtils.toDomain(profissionalRepositoryJPA.save(entity));
    }

    @Override
    public Optional<Profissional> findById(UUID id) {
        Optional<ProfissionalEntity> optionalEntity = profissionalRepositoryJPA.findById(id);
        return optionalEntity.map(persistenceUtils::toDomain);
    }

    @Override
    public void inativarProfissional(Profissional profissional) {
        profissionalRepositoryJPA.inativarProfissional(profissional.getId());
    }

    @Override
    public List<Profissional> listarTodos() {
        return profissionalRepositoryJPA.findAll().stream().map(persistenceUtils::toDomain).toList();
    }

    @Override
    public List<Profissional> listarTodos(String q) {
        return StringUtils.isBlank(q) ?
                listarTodos()
                : listarTodos(
                where(
                        isIdEqualsTo(q))
                        .or(isNomeContains(q))
                        .or(isCargoContains(q))
                        .or(isDataNascimentoEqualsTo(q))
                        .or(isCreatedDateEqualsTo(q))
        );
    }

    public List<Profissional> listarTodos(Specification<ProfissionalEntity> profissionalSpecification) {
        return profissionalRepositoryJPA
                .findAll(profissionalSpecification).stream()
                .map(persistenceUtils::toDomain)
                .toList();
    }


}
