package com.simplesdental.teste.persistence.jpa;

import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfissionalRepositoryJPA extends JpaRepository<ProfissionalEntity, UUID> {
}
