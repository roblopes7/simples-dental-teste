package com.simplesdental.teste.persistence.jpa;

import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ProfissionalRepositoryJPA extends JpaRepository<ProfissionalEntity, UUID>,
        JpaSpecificationExecutor<ProfissionalEntity> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE profissionais p SET p.inativo = true WHERE p.id = :id")
    void inativarProfissional(@Param("id") UUID id);
}
