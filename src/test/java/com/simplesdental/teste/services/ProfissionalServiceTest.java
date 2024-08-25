package com.simplesdental.teste.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import com.simplesdental.teste.services.exceptions.ObjetoNaoEncontradoException;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.impl.ProfissionalServiceImpl;
import com.simplesdental.teste.utils.CriaProfissionalCommandTest;
import com.simplesdental.teste.utils.ProfissionalCommandTest;
import com.simplesdental.teste.utils.ProfissionalTest;
import com.simplesdental.teste.utils.UuidUtils;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfissionalServiceTest {

    private final UuidUtils uuidUtils = new UuidUtils();

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProfissionalServiceImpl profissionalService;

    // TESTES para POST
    @Test
    @DisplayName("Cadastro Profissional com sucesso")
    void cadastrarProfissionalTest() {
        when(profissionalRepository.salvarProfissional(any(Profissional.class)))
                .thenReturn(ProfissionalTest.criaProfissional());
        Profissional profissional = profissionalService.adicionarProfissional(CriaProfissionalCommandTest.criarCommandProfissional());

        verify(profissionalRepository, Mockito.times(1)).salvarProfissional(any(Profissional.class));
        Assertions.assertThat(profissional.getId()).isNotNull();
    }

    @Test
    @DisplayName("Cadastro Profissional com nome vazio")
    void cadastrarProfissionalComNomeVazioTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand("", "DESENVOLVEDOR", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        }, "Campo nome vazio.");
    }

    @Test
    @DisplayName("Cadastro Profissional com nome nulo")
    void cadastrarProfissionalComNomeNuloTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand(null, "DESENVOLVEDOR", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo nome vazio."));
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo vazio")
    void cadastrarProfissionalComCargoVazioTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", "", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo cargo vazio."));
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo nulo")
    void cadastrarProfissionalComCargoNuloTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", null, LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Campo cargo vazio."));
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo inválido")
    void cadastrarProfissionalComCargoInvalidoTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", "Invalido", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Cargo inválido: Invalido"));

    }

    //TESTES para GET por id

    @Test
    @DisplayName("Consultar Profissional com sucesso")
    void consultarProfissionalComSucessoTest() {
        Optional<Profissional> profissional = Optional.of(ProfissionalTest.criaProfissional());
        profissional.get().setId(uuidUtils.getUuidPadrao());
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(profissional);

        Profissional consulta = profissionalService.consultarProfissional(uuidUtils.getUuidPadrao());

        assertNotNull(consulta);
        verify(profissionalRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(consulta.getId()).isEqualTo(uuidUtils.getUuidPadrao());
    }

    @Test
    @DisplayName("Consultar Profissional inexistente")
    void consultarProfissionalInexistenteTest() {;
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            profissionalService.consultarProfissional(uuidUtils.getUuidPadrao());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Profissional de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

    // UPDATE de profissionais

    @Test
    @DisplayName("Atualização de Profissional com sucesso")
    void alterarProfissionalTest() {
        Optional<Profissional> consulta = Optional.of(ProfissionalTest.criaProfissional());
        consulta.get().setId(uuidUtils.getUuidPadrao());
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(consulta);

        final String NOME = "ALTERADO";
        final String CARGO = "TESTER";
        final LocalDate NASCIMENTO = LocalDate.of(1999, 7, 20);

        var alterado = Profissional.builder()
                .id(uuidUtils.getUuidPadrao())
                .nome(NOME)
                .cargo(CARGO)
                .dataNascimento(NASCIMENTO)
                .contatos(new ArrayList<>())
                .build();

        when(profissionalRepository.salvarProfissional(any(Profissional.class)))
                .thenReturn(alterado);
        Profissional profissional = profissionalService.atualizarProfissional(ProfissionalCommandTest.criarCommandProfissional());

        verify(profissionalRepository, Mockito.times(1)).salvarProfissional(any(Profissional.class));
        Assertions.assertThat(profissional.getId()).isEqualTo(uuidUtils.getUuidPadrao());
        Assertions.assertThat(profissional.getNome()).isEqualTo(NOME);
        Assertions.assertThat(profissional.getCargo()).isEqualTo(CARGO);
        Assertions.assertThat(profissional.getDataNascimento()).isEqualTo(NASCIMENTO);
    }

    @Test
    @DisplayName("Alterar Profissional inexistente")
    void alterarProfissionalInexistenteTest() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            profissionalService.atualizarProfissional(ProfissionalCommandTest.criarCommandProfissional());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Profissional de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

    //DETETE de profissionais

    @Test
    @DisplayName("Remover Profissional com sucesso")
    void removerProfissionalTest() {
        var profissional = ProfissionalTest.criaProfissional();
        profissional.setId(uuidUtils.getUuidPadrao());
        when(profissionalRepository.salvarProfissional(any(Profissional.class)))
                .thenReturn(profissional);
        Profissional profissionalSalvo = profissionalService.adicionarProfissional(CriaProfissionalCommandTest.criarCommandProfissional());

        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.of(profissional));

        profissionalService.removerProfissional(uuidUtils.getUuidPadrao());

        verify(profissionalRepository, Mockito.times(1)).inativarProfissional(any());
    }

    @Test
    @DisplayName("Remover Profissional inexistente")
    void removerProfissionalInexistenteTest() {
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            profissionalService.removerProfissional(uuidUtils.getUuidPadrao());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Profissional de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

    // GET de listar/filtrar profissionais

    @Test
    @DisplayName("Listar todos Profissionais com sucesso")
    void listarTodosProfissionaisComSucessoTest() {
        when(profissionalRepository.listarTodos(any(String.class))).thenReturn(ProfissionalTest.listaProfissionaisMock());

        List<Map<String, Object>> lista = profissionalService.listarProfissionais("q", new ArrayList<>());

        assertNotNull(lista);
        verify(profissionalRepository, Mockito.times(1)).listarTodos(any());
        Assertions.assertThat(lista).hasSize(ProfissionalTest.listaProfissionaisMock().size());
    }

    @Test
    @DisplayName("Listar todos Profissionais enviando null fields com sucesso")
    void listarTodosProfissionaisComNullFieldsComSucessoTest() {
        when(profissionalRepository.listarTodos(any(String.class))).thenReturn(ProfissionalTest.listaProfissionaisMock());

        List<Map<String, Object>> lista = profissionalService.listarProfissionais("q", null);

        assertNotNull(lista);
        verify(profissionalRepository, Mockito.times(1)).listarTodos(any());
        Assertions.assertThat(lista).hasSize(ProfissionalTest.listaProfissionaisMock().size());
    }

    @Test
    @DisplayName("Listar todos Profissionais com apenas nome sucesso")
    void listarTodosProfissionaisComApneasNomeTest() {
        when(profissionalRepository.listarTodos(any(String.class))).thenReturn(ProfissionalTest.listaProfissionaisMock());

        List<Map<String, Object>> lista = profissionalService.listarProfissionais("", List.of("nome"));

        assertNotNull(lista);
        verify(profissionalRepository, Mockito.times(1)).listarTodos(any());
        for (Map<String, Object> profissional : lista) {
            Assertions.assertThat(profissional).hasSize(1);
            Assertions.assertThat(profissional.get("nome")).isNotNull();
        }

    }

    @Test
    @DisplayName("listar Profissionais com erro de campo inexistente")
    void listarProfissionaisComCampoInexistenteTest() {
        when(profissionalRepository.listarTodos(any(String.class))).thenReturn(ProfissionalTest.listaProfissionaisMock());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> profissionalService.listarProfissionais("", List.of("teste erro")));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("campo informado não existe em profissional."));
    }

}
