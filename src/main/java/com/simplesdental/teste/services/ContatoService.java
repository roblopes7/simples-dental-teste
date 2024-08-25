package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;

import java.util.UUID;

public interface ContatoService {
    Contato adicionarContato(CriaContatoCommand command);

    Contato consultarContato(UUID id);
}
