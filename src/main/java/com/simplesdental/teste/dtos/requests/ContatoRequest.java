package com.simplesdental.teste.dtos.requests;

import com.simplesdental.teste.commands.ContatoCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContatoRequest {
    private UUID id;
    private String nome;
    private String contato;

    public ContatoCommand toCommand(UUID id) {
        return new ContatoCommand(
                id,
                this.nome,
                this.contato,
                null //TODO: Validar RN se pode alterar profissional do contato
        );
    }
}
