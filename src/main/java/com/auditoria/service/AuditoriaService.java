package com.auditoria.service;

import com.auditoria.model.*;
import com.auditoria.model.enums.ClassificacaoNC;
import com.auditoria.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuditoriaService {
    private final AuditoriaRepository repository;
    private final NaoConformidadeService naoConformidadeService;

    public AuditoriaService(AuditoriaRepository repository, NaoConformidadeService naoConformidadeService) {
        this.repository = repository;
        this.naoConformidadeService = naoConformidadeService;
    }

    @Transactional
    public Auditoria criar(Auditoria auditoria) {
        return repository.save(auditoria);
    }

    public List<Auditoria> listarTodas() {
        return repository.findAll();
    }

    public Auditoria buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Auditoria não encontrada"));
    }

    @Transactional
    public Auditoria adicionarResposta(Long auditoriaId, RespostaChecklist resposta) {
        Auditoria auditoria = buscarPorId(auditoriaId);
        resposta.setAuditoria(auditoria);
        auditoria.getRespostas().add(resposta);
        if ("NAO".equals(resposta.getResultado())) {
            resposta.setGeraNC(true); // Atualiza o flag na resposta

            NaoConformidade nc = new NaoConformidade();
            nc.setRespostaChecklist(resposta);
            nc.setClassificacao(ClassificacaoNC.ADVERTENCIA); // Define uma classificação padrão (ou pegue do item)
            nc.setResponsavel("A Definir"); // Define um responsável padrão

            naoConformidadeService.criar(nc); // Salva a NC
            resposta.setNaoConformidade(nc);
        }

        return repository.save(auditoria);
    }

    @Transactional
    public Auditoria finalizarAuditoria(Long auditoriaId) {
        Auditoria auditoria = buscarPorId(auditoriaId);
        
        int total = auditoria.getRespostas().size();
        long conformes = auditoria.getRespostas().stream()
            .filter(r -> "SIM".equals(r.getResultado()))
            .count();
        long naoAplicaveis = auditoria.getRespostas().stream()
            .filter(r -> "N/A".equals(r.getResultado()))
            .count();
        long naoConformes = auditoria.getRespostas().stream()
            .filter(r -> "NAO".equals(r.getResultado()))
            .count();
        
        int totalAplicavel = total - (int) naoAplicaveis;
        double percentual = totalAplicavel > 0 
            ? (conformes * 100.0) / totalAplicavel 
            : 100.0;
        
        auditoria.setTotalPerguntas(total);
        auditoria.setRespostasConformes((int) conformes);
        auditoria.setRespostasNaoConformes((int) naoConformes);
        auditoria.setRespostasNaoAplicaveis((int) naoAplicaveis);
        auditoria.setPercentualAderencia(Math.round(percentual * 100.0) / 100.0);
        auditoria.setFinalizada(true);
        
        return repository.save(auditoria);
    }

    public List<Auditoria> buscarPorProjeto(String nomeProjeto) {
        return repository.findByNomeProjetoContainingIgnoreCase(nomeProjeto);
    }

    public List<Auditoria> buscarPorAuditor(String auditor) {
        return repository.findByAuditor(auditor);
    }
}