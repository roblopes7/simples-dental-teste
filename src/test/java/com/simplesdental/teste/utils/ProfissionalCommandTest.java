package com.simplesdental.teste.utils;

import com.simplesdental.teste.commands.ProfissionalCommand;

import java.time.LocalDate;

public class ProfissionalCommandTest {

    private static final UuidUtils uuidUtils = new UuidUtils();

    public static ProfissionalCommand criarCommandProfissional() {
        return new ProfissionalCommand(
                uuidUtils.getUuidPadrao(),
                "Teste",
                "DESENVOLVEDOR",
                LocalDate.of(2000, 8, 24)
        );
    }
}
