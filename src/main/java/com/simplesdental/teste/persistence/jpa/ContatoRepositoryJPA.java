package com.simplesdental.teste.persistence.jpa;

import com.simplesdental.teste.persistence.entities.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContatoRepositoryJPA extends JpaRepository<ContatoEntity, UUID>,
        JpaSpecificationExecutor<ContatoEntity> {
}
