package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;

public interface ContatoService {
    Contato adicionarContato(CriaContatoCommand command);
}
