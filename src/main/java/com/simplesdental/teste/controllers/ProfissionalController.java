package com.simplesdental.teste.controllers;

import com.simplesdental.teste.dtos.requests.NovoProfissionalRequest;
import com.simplesdental.teste.dtos.responses.ProfissionalResponse;
import com.simplesdental.teste.services.ProfissionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfissionalController.class);
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfissionalResponse> adicionarProfissional(@RequestBody NovoProfissionalRequest request) {
        LOG.info("m=adicionarProfissional request={}", request);

        var command = request.toCommand();

        var profissionalCriado = profissionalService.adicionarProfissional(command);
        var response = ProfissionalResponse.fromDomain(profissionalCriado);

        return ResponseEntity.created(URI.create("/profissionais/%s".formatted(response.id())))
                .body(response);

    }
}
