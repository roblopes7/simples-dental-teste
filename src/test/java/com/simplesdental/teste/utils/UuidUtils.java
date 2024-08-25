package com.simplesdental.teste.utils;

import java.util.UUID;

public class UuidUtils {

    //UUID padrão para testes, valor fixo gerado aleatório
    public UUID getUuidPadrao() {
        return UUID.fromString("fe979baf-4c64-490e-9070-8c77bf7ba0d7");
    }

    //UUID padrão para testes com FK profissional, valor fixo gerado aleatório
    public UUID getUuidProfissionalTest() {
        return UUID.fromString("091d7f08-c4e2-4a8c-930e-6545e4b54001");
    }

}
