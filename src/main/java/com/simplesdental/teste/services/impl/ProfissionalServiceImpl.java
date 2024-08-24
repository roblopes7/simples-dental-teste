package com.simplesdental.teste.services.impl;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.models.enums.Cargo;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import com.simplesdental.teste.services.ProfissionalService;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public Profissional salvarProfissional(Profissional profissional) {
        return profissionalRepository.salvarProfissional(profissional);
    }

    private void validarProfissional(Profissional profissional) {
        if (StringUtils.isBlank(profissional.getNome())) {
            throw new ValidationException("Campo nome vazio.");
        }

        if(StringUtils.isBlank(profissional.getCargo())) {
            throw new ValidationException("Campo cargo vazio.");
        }else {
            try {
                Cargo.valueOf(profissional.getCargo());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cargo inválido: " + profissional.getCargo());
            }
        }
    }
}
