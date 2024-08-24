package com.simplesdental.teste.models;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profissional {

    private UUID id;
    private String nome;
    private String cargo;
    private LocalDate dataNascimento;
    private LocalDate createdDate;
    private List<Contato> contatos = new ArrayList<>();
}
