package com.simplesdental.teste.dtos.responses;

import com.simplesdental.teste.dtos.ProfissionalDTO;
import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ProfissionalResponse(
        UUID id,
        String nome,
        String cargo,
        LocalDate dataNascimento,
        LocalDate createdDate,
        List<ContatoResponse> contatos
) {
    public static ProfissionalResponse fromDomain(Profissional profissional) {
        List<ContatoResponse> contatosResponse = new ArrayList<>();
        profissional.getContatos().forEach(contato -> contatosResponse.add(toResponse(contato)));

        return new ProfissionalResponse(
                profissional.getId(),
                profissional.getNome(),
                profissional.getCargo(),
                profissional.getDataNascimento(),
                profissional.getCreatedDate(),
                contatosResponse
        );
    }

    public static ProfissionalResponse fromDTO(ProfissionalDTO dto) {
        List<ContatoResponse> contatosResponse = new ArrayList<>();
        if(dto.getContatos() != null && !dto.getContatos().isEmpty()) {
            dto.getContatos().forEach(contato -> contatosResponse.add(toResponse(contato)));
        }

        return new ProfissionalResponse(
                dto.getId(),
                dto.getNome(),
                dto.getCargo(),
                dto.getDataNascimento(),
                dto.getCreatedDate(),
                contatosResponse
        );
    }

    private static ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(
            contato.getId(),
                contato.getNome(),
                contato.getContato(),
                contato.getCreatedDate(),
                contato.getProfissional().getId()
        );
    }
}
