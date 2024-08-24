package com.simplesdental.teste.models;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato {

    private UUID id;
    private String nome;
    private String contato;
    private LocalDate createdDate;
    private Profissional profissional;
}
