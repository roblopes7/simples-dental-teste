package com.simplesdental.teste.models.enums;

public enum Cargo {
    DESENVOLVEDOR("Desenvolvedor"),
    DESIGNER("Designer"),
    SUPORTE("Suporte"),
    TESTER("Tester");

    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
