package com.simplesdental.teste.controllers;

import com.simplesdental.teste.dtos.requests.NovoContatoRequest;
import com.simplesdental.teste.dtos.responses.ContatoResponse;
import com.simplesdental.teste.services.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/contatos")
@Tag(name = "Contatos")
public class ContatoController {

    private static final Logger LOG = LoggerFactory.getLogger(ProfissionalController.class);
    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @Operation(summary = "Cadastrar um Profissional", method = "POST")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoResponse> adicionarContato(@RequestBody NovoContatoRequest request) {
        LOG.info("m=adicionarContato request={}", request);

        var command = request.toCommand();

        var ContatoCriado = contatoService.adicionarContato(command);
        var response = ContatoResponse.fromDomain(ContatoCriado);

        response.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder
                                .methodOn(ProfissionalController.class).consultarProfissional(response.getIdProfissional()))
                        .withRel("profissional"));


        return ResponseEntity.created(URI.create("/contatos/%s".formatted(response.getId())))
                .body(response);

    }

}
