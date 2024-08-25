package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.ContatoCommand;
import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ContatoService {
    Contato adicionarContato(CriaContatoCommand command);

    Contato consultarContato(UUID id);

    Contato atualizarContato(ContatoCommand command);

    void removerContato(UUID id);

    List<Map<String, Object>> filtrarContatos(String q, List<String> fields);
}
