package com.simplesdental.teste.commands;

import java.time.LocalDate;

public record CriaProfissionalCommand(
        String nome,
        String cargo,
        LocalDate dataNascimento
) {}
