package com.simplesdental.teste.dtos.requests;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NovoProfissionalRequest {
    private String nome;
    private String cargo;
    private LocalDate dataNascimento;

    public CriaProfissionalCommand toCommand() {
        return new CriaProfissionalCommand(
                this.nome,
                this.cargo,
                this.dataNascimento
        );
    }

}
