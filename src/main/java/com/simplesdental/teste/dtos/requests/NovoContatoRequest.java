package com.simplesdental.teste.dtos.requests;

import com.simplesdental.teste.commands.CriaContatoCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NovoContatoRequest {
    private String nome;
    private String contato;
    private UUID idProfissional;

    public CriaContatoCommand toCommand() {
        return new CriaContatoCommand(
                this.nome,
                this.contato,
                this.idProfissional
        );
    }
}
