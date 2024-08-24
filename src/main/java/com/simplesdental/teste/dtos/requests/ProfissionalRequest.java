package com.simplesdental.teste.dtos.requests;

import com.simplesdental.teste.commands.ProfissionalCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfissionalRequest {
    private UUID id;
    private String nome;
    private String cargo;
    private LocalDate dataNascimento;

    public ProfissionalCommand toCommand(UUID id) {
        return new ProfissionalCommand(
                id,
                this.nome,
                this.cargo,
                this.dataNascimento
        );
    }
}
