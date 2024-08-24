package com.simplesdental.teste.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record ContatoResponse(
        UUID id,
        String nome,
        String contato,
        LocalDate createdDate,
        UUID idProfissional
) {
}
