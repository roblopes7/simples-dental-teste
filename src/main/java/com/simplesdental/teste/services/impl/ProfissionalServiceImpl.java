package com.simplesdental.teste.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ObjectMapper objectMapper;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository, ObjectMapper objectMapper) {
        this.profissionalRepository = profissionalRepository;
        this.objectMapper = objectMapper;
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

    @Override
    public void removerProfissional(UUID id) {
        var profissional = consultarProfissional(id);
        profissionalRepository.inativarProfissional(profissional);
    }

    @Override
    public List<Map<String, Object>> listarProfissionais(String q, List<String> fields) {
        List<Profissional> profissionais = profissionalRepository.listarTodos(q);

        List<Map<String, Object>> profissionaisDTO = new ArrayList<>();
        boolean todosFields = (fields == null || fields.isEmpty());

        for(Profissional profissional: profissionais) {
            if(todosFields) {
                profissionaisDTO.add(converterModelParaMap(profissional));
            } else {
                profissionaisDTO.add(adicionarCampoDeRetorno(fields, profissional));
            }
        }
        return profissionaisDTO;
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

    private Map<String, Object> adicionarCampoDeRetorno(List<String> fields, Profissional profissional) {
        Map<String, Object> dto = new HashMap<>();
        for (String field : fields) {
            try {
                Field declaredField = Profissional.class.getDeclaredField(field);
                declaredField.setAccessible(true);
                dto.put(field, declaredField.get(profissional));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("campo informado não existe em profissional.");
            }
        }
        return dto;
    }

    private Map<String, Object> converterModelParaMap(Profissional profissional) {
        return objectMapper.convertValue(profissional, Map.class);
    }
}
