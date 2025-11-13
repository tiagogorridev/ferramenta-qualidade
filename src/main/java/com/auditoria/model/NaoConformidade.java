package com.auditoria.model;

import com.auditoria.model.enums.ClassificacaoNC;
import com.auditoria.model.enums.StatusNC;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NaoConformidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoControle;

    @OneToOne
    @JoinColumn(name = "resposta_checklist_id")
    private RespostaChecklist respostaChecklist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoNC classificacao;

    @Column(nullable = false)
    private String responsavel;

    @Column(length = 2000)
    private String acaoCorretiva;

    @Column(nullable = false)
    private LocalDate dataSolicitacao = LocalDate.now();

    @Column(nullable = false)
    private LocalDate prazoResolucao;

    private LocalDate dataResolucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNC status = StatusNC.ABERTA;

    @Column(nullable = false)
    private Integer numeroEscalonamentos = 0;

    @OneToMany(mappedBy = "naoConformidade", cascade = CascadeType.ALL)
    private List<HistoricoEscalonamento> historicoEscalonamentos = new ArrayList<>();

    @Column(length = 2000)
    private String solucaoAdotada;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public NaoConformidade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoControle() {
        return codigoControle;
    }

    public void setCodigoControle(String codigoControle) {
        this.codigoControle = codigoControle;
    }

    public RespostaChecklist getRespostaChecklist() {
        return respostaChecklist;
    }

    public void setRespostaChecklist(RespostaChecklist respostaChecklist) {
        this.respostaChecklist = respostaChecklist;
    }

    public ClassificacaoNC getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(ClassificacaoNC classificacao) {
        this.classificacao = classificacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getAcaoCorretiva() {
        return acaoCorretiva;
    }

    public void setAcaoCorretiva(String acaoCorretiva) {
        this.acaoCorretiva = acaoCorretiva;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDate getPrazoResolucao() {
        return prazoResolucao;
    }

    public void setPrazoResolucao(LocalDate prazoResolucao) {
        this.prazoResolucao = prazoResolucao;
    }

    public LocalDate getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDate dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public StatusNC getStatus() {
        return status;
    }

    public void setStatus(StatusNC status) {
        this.status = status;
    }

    public Integer getNumeroEscalonamentos() {
        return numeroEscalonamentos;
    }

    public void setNumeroEscalonamentos(Integer numeroEscalonamentos) {
        this.numeroEscalonamentos = numeroEscalonamentos;
    }

    public List<HistoricoEscalonamento> getHistoricoEscalonamentos() {
        return historicoEscalonamentos;
    }

    public void setHistoricoEscalonamentos(List<HistoricoEscalonamento> historicoEscalonamentos) {
        this.historicoEscalonamentos = historicoEscalonamentos;
    }

    public String getSolucaoAdotada() {
        return solucaoAdotada;
    }

    public void setSolucaoAdotada(String solucaoAdotada) {
        this.solucaoAdotada = solucaoAdotada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}