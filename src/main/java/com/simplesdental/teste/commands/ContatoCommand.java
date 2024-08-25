package com.simplesdental.teste.commands;

import java.util.UUID;

public record ContatoCommand(
        UUID id,
        String nome,
        String contato,
        UUID idProfissional
) {
}
