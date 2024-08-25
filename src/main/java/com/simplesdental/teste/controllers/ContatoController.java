package com.simplesdental.teste.controllers;

import com.simplesdental.teste.dtos.requests.ContatoRequest;
import com.simplesdental.teste.dtos.requests.NovoContatoRequest;
import com.simplesdental.teste.dtos.requests.ProfissionalRequest;
import com.simplesdental.teste.dtos.responses.ContatoResponse;
import com.simplesdental.teste.dtos.responses.ProfissionalResponse;
import com.simplesdental.teste.services.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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

        adicionarLink(response);

        return ResponseEntity.created(URI.create("/contatos/%s".formatted(response.getId())))
                .body(response);

    }

    @Operation(summary = "Consultar um contato", method = "GET")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoResponse> consultarContato(@PathVariable("id") UUID id) {
        LOG.info("m=consultarContato {}", id);

        var contato = contatoService.consultarContato(id);
        var response = ContatoResponse.fromDomain(contato);
        adicionarLink(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar um contato", method = "PUT")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoResponse> atualizarContato(
            @PathVariable("id") UUID id,
            @RequestBody ContatoRequest request) {
        LOG.info("m=atualizarContato request={}", request);

        var command = request.toCommand(id);

        var contato = contatoService.atualizarContato(command);

        var response = ContatoResponse.fromDomain(contato);
        adicionarLink(response);

        return ResponseEntity.ok(response);
    }

    private void adicionarLink(ContatoResponse response) {
        response.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder
                                .methodOn(ProfissionalController.class).consultarProfissional(response.getIdProfissional()))
                        .withRel("profissional"));
    }
}
