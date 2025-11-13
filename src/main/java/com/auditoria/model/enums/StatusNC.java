package com.auditoria.model.enums;

public enum StatusNC {
    ABERTA("Aberta"),
    EM_RESOLUCAO("Em Resolução"),
    ESCALONADA("Escalonada"),
    RESOLVIDA("Resolvida"),
    CONTESTADA("Contestada");

    private final String descricao;

    StatusNC(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}