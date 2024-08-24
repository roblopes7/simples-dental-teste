package com.simplesdental.teste.services.impl;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.commands.ProfissionalCommand;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.models.enums.Cargo;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import com.simplesdental.teste.services.ProfissionalService;
import com.simplesdental.teste.services.exceptions.ObjetoNaoEncontradoException;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public Profissional adicionarProfissional(CriaProfissionalCommand command) {
        Profissional profissional = Profissional.builder()
                .nome(command.nome())
                .cargo(command.cargo())
                .dataNascimento(command.dataNascimento())
                .contatos(new ArrayList<>())
                .build();

        validarProfissional(profissional);

        return salvarProfissional(profissional);
    }

    @Override
    public Profissional consultarProfissional(UUID id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);

        if (profissional.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Profissional de ID: " + id + " não encontrado");
        }

        return profissional.get();
    }

    @Override
    public Profissional atualizarProfissional(ProfissionalCommand command) {
        Profissional profissional = consultarProfissional(command.id());

        profissional.setNome(command.nome());
        profissional.setCargo(command.cargo());
        profissional.setDataNascimento(command.dataNascimento());

        validarProfissional(profissional);

        return salvarProfissional(profissional);
    }

    public Profissional salvarProfissional(Profissional profissional) {
        return profissionalRepository.salvarProfissional(profissional);
    }

    private void validarProfissional(Profissional profissional) {
        if (StringUtils.isBlank(profissional.getNome())) {
            throw new ValidationException("Campo nome vazio.");
        }

        if (StringUtils.isBlank(profissional.getCargo())) {
            throw new ValidationException("Campo cargo vazio.");
        } else {
            try {
                Cargo.valueOf(profissional.getCargo());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cargo inválido: " + profissional.getCargo());
            }
        }
    }
}
