package com.simplesdental.teste.services;

import com.simplesdental.teste.commands.CriaProfissionalCommand;
import com.simplesdental.teste.models.Profissional;
import com.simplesdental.teste.persistence.repositories.ProfissionalRepository;
import com.simplesdental.teste.services.exceptions.ValidationException;
import com.simplesdental.teste.services.impl.ProfissionalServiceImpl;
import com.simplesdental.teste.utils.CriaProfissionalCommandTest;
import com.simplesdental.teste.utils.ProfissionalTest;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfissionalServiceTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ProfissionalServiceImpl profissionalService;

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
        }, "Campo nome vazio.");
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo vazio")
    void cadastrarProfissionalComCargoVazioTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", "", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        }, "Campo cargo vazio.");
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo nulo")
    void cadastrarProfissionalComCargoNuloTest() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", null, LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        }, "Campo cargo vazio.");
    }

    @Test
    @DisplayName("Cadastro Profissional com cargo inválido")
    void cadastrarProfissionalComCargoInvalidoTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            var command = new CriaProfissionalCommand("Teste", "Invalido", LocalDate.of(2000, 8, 24));
            profissionalService.adicionarProfissional(command);
        }, "Cargo inválido: Invalido");
    }

}
