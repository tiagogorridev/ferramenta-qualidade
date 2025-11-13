package com.auditoria.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class HistoricoEscalonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "nao_conformidade_id")
    private NaoConformidade naoConformidade;

    @Column(nullable = false)
    private String superiorResponsavel;

    @Column(nullable = false)
    private LocalDate dataEscalonamento = LocalDate.now();

    @Column(nullable = false)
    private LocalDate novoPrazoResolucao;

    @Column(length = 1000)
    private String observacoes;

    @Column(nullable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

    public HistoricoEscalonamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NaoConformidade getNaoConformidade() {
        return naoConformidade;
    }

    public void setNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
    }

    public String getSuperiorResponsavel() {
        return superiorResponsavel;
    }

    public void setSuperiorResponsavel(String superiorResponsavel) {
        this.superiorResponsavel = superiorResponsavel;
    }

    public LocalDate getDataEscalonamento() {
        return dataEscalonamento;
    }

    public void setDataEscalonamento(LocalDate dataEscalonamento) {
        this.dataEscalonamento = dataEscalonamento;
    }

    public LocalDate getNovoPrazoResolucao() {
        return novoPrazoResolucao;
    }

    public void setNovoPrazoResolucao(LocalDate novoPrazoResolucao) {
        this.novoPrazoResolucao = novoPrazoResolucao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}