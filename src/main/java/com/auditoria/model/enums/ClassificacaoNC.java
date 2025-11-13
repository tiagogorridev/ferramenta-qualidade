package com.auditoria.model.enums;

public enum ClassificacaoNC {
    ADVERTENCIA("Advertência", 0),
    BAIXA_SIMPLES("Baixa-Simples", 4),
    BAIXA_COMPLEXA("Baixa-Complexa", 5),
    BAIXA_SEVERA("Baixa-Severa", 6),
    BAIXA_EXTREMA("Baixa-Extrema", 7),
    MEDIA_SIMPLES("Média-Simples", 3),
    MEDIA_COMPLEXA("Média-Complexa", 4),
    MEDIA_SEVERA("Média-Severa", 5),
    MEDIA_EXTREMA("Média-Extrema", 6),
    ALTA_SIMPLES("Alta-Simples", 2),
    ALTA_COMPLEXA("Alta-Complexa", 3),
    ALTA_SEVERA("Alta-Severa", 4),
    ALTA_EXTREMA("Alta-Extrema", 5),
    URGENTE_SIMPLES("Urgente-Simples", 1),
    URGENTE_COMPLEXA("Urgente-Complexa", 2),
    URGENTE_SEVERA("Urgente-Severa", 3),
    URGENTE_EXTREMA("Urgente-Extrema", 4);

    private final String descricao;
    private final int diasResolucao;

    ClassificacaoNC(String descricao, int diasResolucao) {
        this.descricao = descricao;
        this.diasResolucao = diasResolucao;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDiasResolucao() {
        return diasResolucao;
    }
}