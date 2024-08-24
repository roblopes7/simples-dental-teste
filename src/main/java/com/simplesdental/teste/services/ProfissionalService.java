package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.models.Profissional;

import java.util.UUID;

public interface ProfissionalService {

    Profissional adicionarProfissional(CriaProfissionalCommand command);

    Profissional consultarProfissional(UUID id);
}
