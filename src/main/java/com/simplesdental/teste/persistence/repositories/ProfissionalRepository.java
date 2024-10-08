package com.simplesdental.teste.persistence.repositories;

import com.simplesdental.teste.models.Profissional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfissionalRepository {
    Profissional salvarProfissional(Profissional profissional);

    Optional<Profissional> findById(UUID id);

    void inativarProfissional(Profissional profissional);

    List<Profissional> filtrarProfissionais(String q);
}
