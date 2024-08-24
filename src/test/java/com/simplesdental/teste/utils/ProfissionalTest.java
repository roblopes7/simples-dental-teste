package com.simplesdental.teste.utils;

import com.simplesdental.teste.models.Profissional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ProfissionalTest {

    public static Profissional criaProfissional() {
        return Profissional.builder()
                .id(UUID.randomUUID())
                .dataNascimento(LocalDate.of(2000, 8, 24))
                .createdDate(LocalDate.now())
                .nome("Teste")
                .cargo("DESENVOLVEDOR")
                .contatos(new ArrayList<>())
                .build();
    }
}
