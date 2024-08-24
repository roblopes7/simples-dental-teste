package com.simplesdental.teste.services;

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
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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
    void alterarProfissionalInexistenteTest() {;
        when(profissionalRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            profissionalService.atualizarProfissional(ProfissionalCommandTest.criarCommandProfissional());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Profissional de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }
}
