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

    public static Cargo toEnum(String descricao) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.descricao.equals(descricao) || cargo.name().equals(descricao)) {
                return cargo;
            }
        }
        return null;
    }
}
