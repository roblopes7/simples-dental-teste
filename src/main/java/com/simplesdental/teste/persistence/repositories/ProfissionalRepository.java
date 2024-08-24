package com.simplesdental.teste.persistence.repositories;

import com.simplesdental.teste.models.Profissional;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalRepository {
    Profissional salvarProfissional(Profissional profissional);

    Optional<Profissional> findById(UUID id);
}
