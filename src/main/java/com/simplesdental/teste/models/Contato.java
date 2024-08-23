package com.simplesdental.teste.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "contatos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "contato")
    private String contato;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private Profissional profissional;
}
