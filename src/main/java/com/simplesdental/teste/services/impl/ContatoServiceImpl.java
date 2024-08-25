package com.simplesdental.teste.services.impl;

import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.services.ContatoService;
import com.simplesdental.teste.services.ProfissionalService;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContatoServiceImpl implements ContatoService {

    private final ContatoRepository contatoRepository;
    private final ProfissionalService profissionalService;

    public ContatoServiceImpl(ContatoRepository contatoRepository, ProfissionalService profissionalService) {
        this.contatoRepository = contatoRepository;
        this.profissionalService = profissionalService;
    }

    @Override
    public Contato adicionarContato(CriaContatoCommand command) {
        var profissional = consultarProfissional(command.idProfissional());
        var contato = Contato.builder()
                .nome(command.nome())
                .contato(command.contato())
                .idProfissional(profissional.getId())
                .build();

        validarContato(contato);
        return contatoRepository.salvarContato(contato);
    }

    private void validarContato(Contato contato) {
        if (StringUtils.isBlank(contato.getNome())) {
            throw new ValidationException("Campo nome vazio.");
        }

        if (StringUtils.isBlank(contato.getContato())) {
            throw new ValidationException("Campo contato vazio.");
        }

        if (contato.getIdProfissional() == null) {
            throw new ValidationException("Campo profissional vazio.");
        }
    }

    private Profissional consultarProfissional(UUID id) {
        if (id == null) {
            throw new ValidationException("Profissional não informado.");
        }
        return profissionalService.consultarProfissional(id);
    }
}
