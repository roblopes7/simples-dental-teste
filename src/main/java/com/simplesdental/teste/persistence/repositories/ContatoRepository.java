package com.simplesdental.teste.persistence.repositories;

import com.simplesdental.teste.models.Contato;

import java.util.Optional;
import java.util.UUID;

public interface ContatoRepository {
    Contato salvarContato(Contato contato);

    Optional<Contato> findById(UUID id);
}
