package com.simplesdental.teste.persistence.specifications;

import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class ProfissionalSpecification {

    public static Specification<ProfissionalEntity> isIdEqualsTo(String id) {
        if (id == null|| id.isEmpty()) {
            return null;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), uuid);
    }


    public static Specification<ProfissionalEntity> isNomeContains(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<ProfissionalEntity> isCargoContains(String cargo) {
        if (cargo == null || cargo.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("cargo")), "%" + cargo.toUpperCase() + "%");
    }

    public static Specification<ProfissionalEntity> isDataNascimentoEqualsTo(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(data);
        } catch (Exception e) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataNascimento"), localDate);
    }

    public static Specification<ProfissionalEntity> isCreatedDateEqualsTo(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(data);
        } catch (Exception e) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdDate"), localDate);
    }


}
