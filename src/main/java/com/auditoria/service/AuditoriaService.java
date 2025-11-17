package com.auditoria.service;

import com.auditoria.model.*;
import com.auditoria.model.enums.ClassificacaoNC;
import com.auditoria.model.enums.StatusNC;
import com.auditoria.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        
        RespostaChecklist respostaExistente = null;
        for (RespostaChecklist r : auditoria.getRespostas()) {
            if (r.getItemChecklist().getId().equals(resposta.getItemChecklist().getId())) {
                respostaExistente = r;
                break;
            }
        }

        if (respostaExistente != null) {
            respostaExistente.setResultado(resposta.getResultado());
            respostaExistente.setObservacoes(resposta.getObservacoes());
            
            if ("NAO".equals(resposta.getResultado())) {
                respostaExistente.setGeraNC(true);
                
                NaoConformidade ncNova = resposta.getNaoConformidade();
                NaoConformidade ncAntiga = respostaExistente.getNaoConformidade();
                
                if (ncAntiga == null) { 
                    ncAntiga = new NaoConformidade();
                }

                if (ncNova != null) {
                    ncAntiga.setClassificacao(ncNova.getClassificacao());
                    ncAntiga.setAcaoCorretiva(ncNova.getAcaoCorretiva());
                    ncAntiga.setPrazoResolucao(ncNova.getPrazoResolucao());
                    ncAntiga.setEmailResponsavel(ncNova.getEmailResponsavel());
                    
                    if (ncAntiga.getId() == null) {
                        ncAntiga.setResponsavel(auditoria.getAuditor());
                        ncAntiga.setCodigoControle("NC-" + System.currentTimeMillis());
                        ncAntiga.setStatus(StatusNC.ABERTA);
                        ncAntiga.setDataSolicitacao(LocalDate.now());
                    }
                    
                    ncAntiga.setRespostaChecklist(respostaExistente);
                    respostaExistente.setNaoConformidade(ncAntiga);
                }
            } else {
                respostaExistente.setGeraNC(false);
                if (respostaExistente.getNaoConformidade() != null) {
                    respostaExistente.setNaoConformidade(null);
                }
            }
            
        } else {
            resposta.setAuditoria(auditoria);
            auditoria.getRespostas().add(resposta);
            
            if ("NAO".equals(resposta.getResultado())) {
                resposta.setGeraNC(true);
                NaoConformidade nc = resposta.getNaoConformidade();
                if (nc != null) {
                    nc.setClassificacao(nc.getClassificacao());
                    nc.setAcaoCorretiva(nc.getAcaoCorretiva());
                    nc.setPrazoResolucao(nc.getPrazoResolucao());
                    nc.setEmailResponsavel(nc.getEmailResponsavel());
                    
                    nc.setResponsavel(auditoria.getAuditor());
                    nc.setCodigoControle("NC-" + System.currentTimeMillis());
                    nc.setStatus(StatusNC.ABERTA);
                    nc.setDataSolicitacao(LocalDate.now());
                    nc.setRespostaChecklist(resposta);
                    resposta.setNaoConformidade(nc);
                }
            }
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