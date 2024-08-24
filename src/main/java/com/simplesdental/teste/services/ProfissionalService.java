package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.models.Profissional;

public interface ProfissionalService {

    Profissional adicionarProfissional(CriaProfissionalCommand command);
}
