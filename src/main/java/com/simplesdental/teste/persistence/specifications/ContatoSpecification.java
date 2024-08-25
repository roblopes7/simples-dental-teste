package com.simplesdental.teste.persistence.specifications;

import com.simplesdental.teste.persistence.entities.ContatoEntity;
import com.simplesdental.teste.persistence.entities.ProfissionalEntity;
import com.simplesdental.teste.services.utils.StringUtils;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class ContatoSpecification {

    public static Specification<ContatoEntity> isIdEqualsTo(String id) {
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

    public static Specification<ContatoEntity> isNomeContains(String nome) {
        if (StringUtils.isBlank(nome)) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<ContatoEntity> isContatoContains(String contato) {
        if (StringUtils.isBlank(contato)) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("contato")), "%" + contato.toUpperCase() + "%");
    }

    public static Specification<ContatoEntity> isCreatedDateEqualsTo(String data) {
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

    public static Specification<ContatoEntity> isIdProfissionalEqualsTo(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            Join<ContatoEntity, ProfissionalEntity> profissionalJoin = root.join("profissional");
            return criteriaBuilder.equal(profissionalJoin.get("id"), uuid);
        };
    }


}
