package com.simplesdental.teste.commands;

import java.time.LocalDate;
import java.util.UUID;

public record ProfissionalCommand(
        UUID id,
        String nome,
        String cargo,
        LocalDate dataNascimento
) {}
