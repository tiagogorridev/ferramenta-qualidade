package com.auditoria.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeProjeto;

    @Column(nullable = false)
    private String auditor;

    @Column(nullable = false)
    private LocalDateTime dataAuditoria = LocalDateTime.now();

    private Integer tempoAuditoria;

    @Column(length = 2000)
    private String descricaoAvaliacao;

    private Double percentualAderencia;
    
    private Integer totalPerguntas;
    
    private Integer respostasConformes;
    
    private Integer respostasNaoConformes;
    
    private Integer respostasNaoAplicaveis;

    @JsonManagedReference
    @OneToMany(mappedBy = "auditoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostaChecklist> respostas = new ArrayList<>();

    @Column(nullable = false)
    private Boolean finalizada = false;

    public Auditoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public LocalDateTime getDataAuditoria() {
        return dataAuditoria;
    }

    public void setDataAuditoria(LocalDateTime dataAuditoria) {
        this.dataAuditoria = dataAuditoria;
    }

    public Integer getTempoAuditoria() {
        return tempoAuditoria;
    }

    public void setTempoAuditoria(Integer tempoAuditoria) {
        this.tempoAuditoria = tempoAuditoria;
    }

    public String getDescricaoAvaliacao() {
        return descricaoAvaliacao;
    }

    public void setDescricaoAvaliacao(String descricaoAvaliacao) {
        this.descricaoAvaliacao = descricaoAvaliacao;
    }

    public Double getPercentualAderencia() {
        return percentualAderencia;
    }

    public void setPercentualAderencia(Double percentualAderencia) {
        this.percentualAderencia = percentualAderencia;
    }

    public Integer getTotalPerguntas() {
        return totalPerguntas;
    }

    public void setTotalPerguntas(Integer totalPerguntas) {
        this.totalPerguntas = totalPerguntas;
    }

    public Integer getRespostasConformes() {
        return respostasConformes;
    }

    public void setRespostasConformes(Integer respostasConformes) {
        this.respostasConformes = respostasConformes;
    }

    public Integer getRespostasNaoConformes() {
        return respostasNaoConformes;
    }

    public void setRespostasNaoConformes(Integer respostasNaoConformes) {
        this.respostasNaoConformes = respostasNaoConformes;
    }

    public Integer getRespostasNaoAplicaveis() {
        return respostasNaoAplicaveis;
    }

    public void setRespostasNaoAplicaveis(Integer respostasNaoAplicaveis) {
        this.respostasNaoAplicaveis = respostasNaoAplicaveis;
    }

    public List<RespostaChecklist> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaChecklist> respostas) {
        this.respostas = respostas;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }
}