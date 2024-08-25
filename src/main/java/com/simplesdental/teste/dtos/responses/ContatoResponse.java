package com.simplesdental.teste.dtos.responses;

import com.simplesdental.teste.models.Contato;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ContatoResponse  extends RepresentationModel<ContatoResponse> implements Serializable {

    private UUID id;
    private String nome;
    private String contato;
    private LocalDate createdDate;
    private UUID idProfissional;

    public static ContatoResponse fromDomain(Contato contato) {
        return new ContatoResponse(
                contato.getId(),
                contato.getNome(),
                contato.getContato(),
                contato.getCreatedDate(),
                contato.getIdProfissional()
        );
    }

}
