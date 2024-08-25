package com.simplesdental.teste.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.teste.commands.ContatoCommand;
import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.services.ContatoService;
import com.simplesdental.teste.services.ProfissionalService;
import com.simplesdental.teste.services.exceptions.ObjetoNaoEncontradoException;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ContatoServiceImpl implements ContatoService {

    private final ContatoRepository contatoRepository;
    private final ProfissionalService profissionalService;
    private final ObjectMapper objectMapper;

    public ContatoServiceImpl(ContatoRepository contatoRepository, ProfissionalService profissionalService, ObjectMapper objectMapper) {
        this.contatoRepository = contatoRepository;
        this.profissionalService = profissionalService;
        this.objectMapper = objectMapper;
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
        return salvarContato(contato);
    }

    @Override
    public Contato consultarContato(UUID id) {
        Optional<Contato> contato = contatoRepository.findById(id);

        if (contato.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Contato de ID: " + id + " não encontrado");
        }

        return contato.get();
    }

    @Override
    public Contato atualizarContato(ContatoCommand command) {
        Contato contato = consultarContato(command.id());

        contato.setNome(command.nome());
        contato.setContato(command.contato());

        validarContato(contato);

        return salvarContato(contato);
    }

    @Override
    public void removerContato(UUID id) {
        var contato = consultarContato(id);
        contatoRepository.removerContato(id);
    }

    @Override
    public List<Map<String, Object>> filtrarContatos(String q, List<String> fields) {
        List<Contato> contatos = contatoRepository.filtrarContatos(q);

        List<Map<String, Object>> contatosDTO = new ArrayList<>();
        boolean todosFields = (fields == null || fields.isEmpty());

        for(Contato contato: contatos) {
            if(todosFields) {
                contatosDTO.add(converterModelParaMap(contato));
            } else {
                contatosDTO.add(adicionarCampoDeRetorno(fields, contato));
            }
        }
        return contatosDTO;
    }

    private Contato salvarContato(Contato contato) {
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

    private Map<String, Object> adicionarCampoDeRetorno(List<String> fields, Contato contato) {
        Map<String, Object> dto = new HashMap<>();
        for (String field : fields) {
            try {
                Field declaredField = Contato.class.getDeclaredField(field);
                declaredField.setAccessible(true);
                dto.put(field, declaredField.get(contato));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("campo informado não existe em contato.");
            }
        }
        return dto;
    }

    private Map<String, Object> converterModelParaMap(Contato contato) {
        return objectMapper.convertValue(contato, Map.class);
    }
}
