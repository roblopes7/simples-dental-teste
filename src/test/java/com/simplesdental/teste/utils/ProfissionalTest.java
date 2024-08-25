package com.simplesdental.teste.utils;

import com.simplesdental.teste.models.Profissional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public static List<Profissional> listaProfissionaisMock() {
        return List.of(
                Profissional.builder().id(UUID.randomUUID()).nome("TESTE 1").cargo("DESIGNER").dataNascimento(LocalDate.of(2000,1,6)).build(),
                Profissional.builder().id(UUID.randomUUID()).nome("TESTE 2").cargo("TESTER").dataNascimento(LocalDate.of(2001,2,10)).build(),
                Profissional.builder().id(UUID.randomUUID()).nome("TESTE 3").cargo("SUPORTE").dataNascimento(LocalDate.of(2002,3,15)).build(),
                Profissional.builder().id(UUID.randomUUID()).nome("TESTE 4").cargo("SUPORTE").dataNascimento(LocalDate.of(2003,4,20)).build(),
                Profissional.builder().id(UUID.randomUUID()).nome("TESTE 5").cargo("DESENVOLVEDOR").dataNascimento(LocalDate.of(2004,5,25)).build()
        );
    }
}
