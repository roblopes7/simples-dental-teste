package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.commands.ProfissionalCommand;
import com.simplesdental.teste.models.Profissional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProfissionalService {

    Profissional adicionarProfissional(CriaProfissionalCommand command);

    Profissional consultarProfissional(UUID id);

    Profissional atualizarProfissional(ProfissionalCommand command);

    void removerProfissional(UUID id);

    List<Map<String, Object>> filtrarProfissionais(String q, List<String> fields);
}
