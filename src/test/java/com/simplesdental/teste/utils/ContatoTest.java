package com.simplesdental.teste.utils;

import com.simplesdental.teste.models.Contato;

import java.time.LocalDate;
import java.util.UUID;

public class ContatoTest {

    private static UuidUtils uuidUtils = new UuidUtils();

    public static Contato criaContato() {
        return new Contato(
                UUID.randomUUID(),
                "Nome Teste",
                "Contato Teste",
                LocalDate.of(2024,8,25),
                uuidUtils.getUuidProfissionalTest()
        );
    }
}
