package com.simplesdental.teste.persistence.entities;

import com.simplesdental.teste.models.enums.Cargo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "profissionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @Column(name = "nascimento")
    private LocalDate dataNascimento;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @Column(name = "inativo")
    private boolean inativo = false;

    @OneToMany(mappedBy = "profissional", cascade = {},
            targetEntity = ContatoEntity.class)
    private List<ContatoEntity> contatos = new ArrayList<>();
}