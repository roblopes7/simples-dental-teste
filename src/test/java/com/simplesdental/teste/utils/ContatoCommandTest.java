package com.simplesdental.teste.utils;

import com.simplesdental.teste.commands.ContatoCommand;

public class ContatoCommandTest {

    private static final UuidUtils uuidUtils = new UuidUtils();

    public static ContatoCommand criarCommandContato() {
        return new ContatoCommand(
                uuidUtils.getUuidPadrao(),
                "Teste",
                "contato teste",
                uuidUtils.getUuidProfissionalTest()
        );
    }
}
