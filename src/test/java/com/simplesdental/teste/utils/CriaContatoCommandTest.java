package com.simplesdental.teste.utils;

import com.simplesdental.teste.commands.CriaContatoCommand;

public class CriaContatoCommandTest {

    private static UuidUtils uuidUtils = new UuidUtils();

    public static CriaContatoCommand criarCommandContato() {
        return new CriaContatoCommand(
                "Nome Teste",
                "Contato Teste",
                uuidUtils.getUuidProfissionalTest()
        );
    }
}
