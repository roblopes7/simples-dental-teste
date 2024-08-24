package com.simplesdental.teste.persistence.repositories.impl;

import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.models.enums.Cargo;
import com.simplesdental.teste.persistence.entities.ContatoEntity;
import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import com.simplesdental.teste.persistence.jpa.ProfissionalRepositoryJPA;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProfissionalRepositoryImpl implements ProfissionalRepository {

    private final ProfissionalRepositoryJPA profissionalRepositoryJPA;

    public ProfissionalRepositoryImpl(ProfissionalRepositoryJPA profissionalRepositoryJPA) {
        this.profissionalRepositoryJPA = profissionalRepositoryJPA;
    }

    @Override
    public Profissional salvarProfissional(Profissional profissional) {
        var entity = ProfissionalEntity.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .cargo(Cargo.valueOf(profissional.getCargo()))
                .dataNascimento(profissional.getDataNascimento())
                .contatos(profissional.getContatos().stream().map(this::toContatoEntity).toList())
                .createdDate(profissional.getCreatedDate())
                .build();

        if (entity.getId() == null) {
            entity.setCreatedDate(LocalDate.now());
        }

        return toDomain(profissionalRepositoryJPA.save(entity));
    }

    @Override
    public Optional<Profissional> findById(UUID id) {
        Optional<ProfissionalEntity> optionalEntity = profissionalRepositoryJPA.findById(id);
        return optionalEntity.map(this::toDomain);
    }

    private ContatoEntity toContatoEntity(Contato contato) {
        return ContatoEntity.builder()
                .id(contato.getId())
                .contato(contato.getContato())
                .nome(contato.getNome())
                .createdDate(contato.getCreatedDate())
                .build();
    }

    private Profissional toDomain(ProfissionalEntity entity) {
        return Profissional.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cargo(entity.getCargo().toString())
                .createdDate(entity.getCreatedDate())
                .dataNascimento(entity.getDataNascimento())
                .contatos(entity.getContatos().stream().map(this::toDomain).toList())
                .build();
    }

    //TODO: reavaliar para reaproveitar na ContatoRepositoryImpl se necessário
    private Contato toDomain(ContatoEntity entity) {
        return Contato.builder()
                .id(entity.getId())
                .contato(entity.getContato())
                .nome(entity.getNome())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
