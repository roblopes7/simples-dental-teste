package com.simplesdental.teste.utils;

import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;

import java.time.LocalDate;
import java.util.List;
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

    public static List<Contato> listaContatosMock() {
        return List.of(
                Contato.builder().id(UUID.randomUUID()).nome("TESTE 1").contato("CONTATO 1").idProfissional(uuidUtils.getUuidProfissionalTest()).build(),
                Contato.builder().id(UUID.randomUUID()).nome("TESTE 2").contato("CONTATO 2").idProfissional(uuidUtils.getUuidProfissionalTest()).build(),
                Contato.builder().id(UUID.randomUUID()).nome("TESTE 3").contato("CONTATO 3").idProfissional(uuidUtils.getUuidProfissionalTest()).build(),
                Contato.builder().id(UUID.randomUUID()).nome("TESTE 4").contato("CONTATO 4").idProfissional(uuidUtils.getUuidProfissionalTest()).build()
        );
    }
}
