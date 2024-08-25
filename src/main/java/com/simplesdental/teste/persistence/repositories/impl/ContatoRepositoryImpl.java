package com.simplesdental.teste.persistence.repositories.impl;

import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.persistence.entities.ContatoEntity;
import com.simplesdental.teste.persistence.jpa.ContatoRepositoryJPA;
import com.simplesdental.teste.persistence.jpa.ProfissionalRepositoryJPA;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.persistence.utils.ModelsPersistenceUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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

}
