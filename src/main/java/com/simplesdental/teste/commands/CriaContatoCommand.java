package com.simplesdental.teste.commands;

import java.util.UUID;

public record CriaContatoCommand (
        String nome,
        String contato,
        UUID idProfissional
){
}
