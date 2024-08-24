package com.simplesdental.teste.utils;

import com.simplesdental.teste.commands.CriaProfissionalCommand;

import java.time.LocalDate;

public class CriaProfissionalCommandTest {

    public static CriaProfissionalCommand criarCommandProfissional() {
        return new CriaProfissionalCommand("Teste",
                "DESENVOLVEDOR",
                LocalDate.of(2000, 8, 24)
        );
    }
}
