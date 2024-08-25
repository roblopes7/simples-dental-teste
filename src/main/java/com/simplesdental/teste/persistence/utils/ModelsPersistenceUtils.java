package com.simplesdental.teste.persistence.utils;

import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.models.enums.Cargo;
import com.simplesdental.teste.persistence.entities.ContatoEntity;
import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import org.springframework.stereotype.Component;

@Component
public class ModelsPersistenceUtils {
    public Contato toDomain(ContatoEntity entity) {
        return Contato.builder()
                .id(entity.getId())
                .contato(entity.getContato())
                .nome(entity.getNome())
                .createdDate(entity.getCreatedDate())
                .idProfissional(entity.getProfissional().getId())
                .build();
    }

    public ContatoEntity toEntity(Contato contato) {
        return ContatoEntity.builder()
                .id(contato.getId())
                .nome(contato.getNome())
                .contato(contato.getContato())
                //.profissional(toEntity(contato.getProfissional()))
                .createdDate(contato.getCreatedDate())
                .build();
    }

    public Profissional toDomain(ProfissionalEntity entity) {
        return Profissional.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cargo(entity.getCargo().toString())
                .createdDate(entity.getCreatedDate())
                .dataNascimento(entity.getDataNascimento())
                .contatos(entity.getContatos().stream().map(this::toDomain).toList())
                .build();
    }

    public  ProfissionalEntity toEntity(Profissional profissional) {
        return ProfissionalEntity.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .cargo(Cargo.toEnum(profissional.getCargo()))
                .dataNascimento(profissional.getDataNascimento())
                .contatos(profissional.getContatos().stream().map(this::toEntity).toList())
                .createdDate(profissional.getCreatedDate())
                .build();
    }
}
