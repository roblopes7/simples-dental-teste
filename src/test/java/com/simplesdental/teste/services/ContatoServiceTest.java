package com.simplesdental.teste.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.teste.commands.CriaContatoCommand;
import com.simplesdental.teste.models.Contato;
import com.simplesdental.teste.persistence.repositories.ContatoRepository;
import com.simplesdental.teste.persistence.utils.ModelsPersistenceUtils;
import com.simplesdental.teste.services.exceptions.ObjetoNaoEncontradoException;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    //TESTES para GET por id

    @Test
    @DisplayName("Consultar Contato com sucesso")
    void consultarContatoComSucessoTest() {
        Optional<Contato> contato = Optional.of(ContatoTest.criaContato());
        contato.get().setId(uuidUtils.getUuidPadrao());
        when(contatoRepository.findById(any(UUID.class))).thenReturn(contato);

        Contato consulta = contatoService.consultarContato(uuidUtils.getUuidPadrao());

        assertNotNull(consulta);
        verify(contatoRepository, Mockito.times(1)).findById(any());
        Assertions.assertThat(consulta.getId()).isEqualTo(uuidUtils.getUuidPadrao());
    }

    @Test
    @DisplayName("Consultar Contato inexistente")
    void consultarContatoInexistenteTest() {;
        when(contatoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            contatoService.consultarContato(uuidUtils.getUuidPadrao());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Contato de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

    // UPDATE de contatos

    @Test
    @DisplayName("Atualização de Contato com sucesso")
    void alterarContatoTest() {
        when(profissionalService.consultarProfissional(any(UUID.class)))
                .thenReturn(ProfissionalTest.criaProfissional());

        Optional<Contato> consulta = Optional.of(ContatoTest.criaContato());
        consulta.get().setId(uuidUtils.getUuidPadrao());
        when(contatoRepository.findById(any(UUID.class))).thenReturn(consulta);

        final String NOME = "NOME ALTERADO";
        final String CONTATO = "CONTATO ALTERADO";

        var alterado = Contato.builder()
                .id(uuidUtils.getUuidPadrao())
                .nome(NOME)
                .contato(CONTATO)
                .idProfissional(uuidUtils.getUuidProfissionalTest())
                .build();

        when(contatoRepository.salvarContato(any(Contato.class)))
                .thenReturn(alterado);
        Contato contato = contatoService.atualizarContato(ContatoCommandTest.criarCommandContato());

        verify(contatoRepository, Mockito.times(1)).salvarContato(any(Contato.class));
        Assertions.assertThat(contato.getId()).isEqualTo(uuidUtils.getUuidPadrao());
        Assertions.assertThat(contato.getNome()).isEqualTo(NOME);
        Assertions.assertThat(contato.getContato()).isEqualTo(CONTATO);
    }

    @Test
    @DisplayName("Alterar Contato inexistente")
    void alterarContatoInexistenteTest() {
        when(contatoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            contatoService.atualizarContato(ContatoCommandTest.criarCommandContato());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Contato de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

    //DETETE de contatos

    @Test
    @DisplayName("Remover Contato com sucesso")
    void removerContatoTest() {
        when(contatoRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(ContatoTest.criaContato()));

        contatoService.removerContato(uuidUtils.getUuidPadrao());

        verify(contatoRepository, Mockito.times(1)).removerContato(any());
    }

    @Test
    @DisplayName("Remover Profissional inexistente")
    void removerProfissionalInexistenteTest() {
        when(contatoRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        ObjetoNaoEncontradoException exception = assertThrows(ObjetoNaoEncontradoException.class, () -> {
            contatoService.removerContato(uuidUtils.getUuidPadrao());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("Contato de ID: " + uuidUtils.getUuidPadrao() + " não encontrado"));
    }

}
