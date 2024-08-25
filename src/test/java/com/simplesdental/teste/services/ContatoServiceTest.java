package com.simplesdental.teste.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.persistence.utils.ModelsPersistenceUtils;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.impl.ContatoServiceImpl;
import com.simplesdental.teste.services.impl.ProfissionalServiceImpl;
import com.simplesdental.teste.utils.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContatoServiceTest {

    private final UuidUtils uuidUtils = new UuidUtils();

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ProfissionalServiceImpl profissionalService;

    @Mock
    private ModelsPersistenceUtils modelsPersistenceUtils;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ContatoServiceImpl contatoService;

    //TESTES para POST

    @Test
    @DisplayName("Cadastro Contato com sucesso")
    void cadastrarContatoTest() {
        when(contatoRepository.salvarContato(any(Contato.class)))
                .thenReturn(ContatoTest.criaContato());

        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        Contato contato = contatoService.adicionarContato(CriaContatoCommandTest.criarCommandContato());

        verify(contatoRepository, Mockito.times(1)).salvarContato(any(Contato.class));
        Assertions.assertThat(contato.getId()).isNotNull();
    }

    @Test
    @DisplayName("Cadastro Contato com nome vazio")
    void cadastrarContatoComNomeVazioTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaContatoCommand("", "Contato", UUID.randomUUID());
            contatoService.adicionarContato(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo nome vazio."));
    }

    @Test
    @DisplayName("Cadastro Contato com nome nulo")
    void cadastrarContatoComNomeNuloTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaContatoCommand(null, "Contato", UUID.randomUUID());
            contatoService.adicionarContato(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo nome vazio."));
    }

    @Test
    @DisplayName("Cadastro Contato com contato vazio")
    void cadastrarContatoComCargoVazioTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaContatoCommand("nome", "", UUID.randomUUID());
            contatoService.adicionarContato(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo contato vazio."));
    }

    @Test
    @DisplayName("Cadastro Contato com contato nulo")
    void cadastrarContatoComContatoNuloTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaContatoCommand("teste", null, UUID.randomUUID());
            contatoService.adicionarContato(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo contato vazio."));
    }

    @Test
    @DisplayName("Cadastro Contato com profissional nulo")
    void cadastrarContatoComProfissionalNuloTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaContatoCommand("teste", "contato", null);
            contatoService.adicionarContato(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Profissional não informado."));
    }

}
