package com.simplesdental.teste.controllers;

import com.simplesdental.teste.dtos.requests.NovoProfissionalRequest;
import com.simplesdental.teste.dtos.requests.ProfissionalRequest;
import com.simplesdental.teste.dtos.responses.ProfissionalResponse;
import com.simplesdental.teste.services.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfissionalController.class);
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @Operation(summary = "Cadastrar um Profissional", method = "POST")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfissionalResponse> adicionarProfissional(@RequestBody NovoProfissionalRequest request) {
        LOG.info("m=adicionarProfissional request={}", request);

        var command = request.toCommand();

        var profissionalCriado = profissionalService.adicionarProfissional(command);
        var response = ProfissionalResponse.fromDomain(profissionalCriado);

        return ResponseEntity.created(URI.create("/profissionais/%s".formatted(response.id())))
                .body(response);

    }

    @Operation(summary = "Consultar um profissional", method = "GET")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfissionalResponse> consultarProfissional(@PathVariable("id") UUID id) {
        LOG.info("m=consultarProfissional {}", id);

        var profissional = profissionalService.consultarProfissional(id);

        return new ResponseEntity<>(ProfissionalResponse.fromDomain(profissional), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar um profissional", method = "PUT")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfissionalResponse> atualizarProfissional(
            @PathVariable("id") UUID id,
            @RequestBody ProfissionalRequest request) {
        LOG.info("m=atualizarProfissional request={}", request);

        var command = request.toCommand(id);

        var profissional = profissionalService.atualizarProfissional(command);

        var response = ProfissionalResponse.fromDomain(profissional);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover/inativar um profissional", method = "DELETE")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerProfissional(@PathVariable("id") UUID id) {
        LOG.info("m=removerProfissional id={}", id);
        profissionalService.removerProfissional(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Listar/Filtrar profissionais", method = "GET")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> listarProfissionais(
            @RequestParam("q") String q,
            @RequestParam(value = "fields", required = false) List<String> fields
    ) {
        LOG.info("m=listarProfissionais {}", q);

        List<Map<String, Object>> profissionais = profissionalService.listarProfissionais(q, fields);

        return ResponseEntity.ok(profissionais);
    }


}
