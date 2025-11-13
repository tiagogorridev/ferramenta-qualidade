package com.auditoria.service;

import com.auditoria.model.*;
import com.auditoria.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuditoriaService {
    private final AuditoriaRepository repository;

    public AuditoriaService(AuditoriaRepository repository) {
        this.repository = repository;
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