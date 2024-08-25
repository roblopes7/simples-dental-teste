package com.simplesdental.teste.dtos;

import com.simplesdental.teste.models.Contato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalDTO {
    private UUID id;
    private String nome;
    private String cargo;
    private LocalDate dataNascimento;
    private List<Contato> contatos;
    private LocalDate createdDate;
}
