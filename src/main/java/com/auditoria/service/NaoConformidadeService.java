package com.auditoria.service;

import com.auditoria.model.HistoricoEscalonamento;
import com.auditoria.model.NaoConformidade;
import com.auditoria.model.enums.StatusNC;
import com.auditoria.repository.NaoConformidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class NaoConformidadeService {
    private final NaoConformidadeRepository repository;

    public NaoConformidadeService(NaoConformidadeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public NaoConformidade criar(NaoConformidade nc) {
        if (nc.getCodigoControle() == null) {
             String codigo = "NC-" + System.currentTimeMillis();
             nc.setCodigoControle(codigo);
        }
       
        if (nc.getPrazoResolucao() == null) {
            LocalDate prazo = LocalDate.now()
                .plusDays(nc.getClassificacao().getDiasResolucao());
            nc.setPrazoResolucao(prazo);
        }
        
        return repository.save(nc);
    }

    public List<NaoConformidade> listarTodas() {
        return repository.findAll();
    }

    public List<NaoConformidade> listarAbertas() {
        return repository.findNaoConformidadesAbertas();
    }

    public List<NaoConformidade> listarPorStatus(StatusNC status) {
        return repository.findByStatus(status);
    }

    public List<NaoConformidade> listarPorResponsavel(String responsavel) {
        return repository.findByResponsavel(responsavel);
    }

    public List<NaoConformidade> listarAtrasadas() {
        return repository.findByPrazoResolucaoBeforeAndStatusNot(
            LocalDate.now(), StatusNC.RESOLVIDA
        );
    }

    public NaoConformidade buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("NC não encontrada"));
    }

    public NaoConformidade buscarPorIdCompletamente(Long id) {
        return repository.findByIdCompletamente(id)
            .orElseThrow(() -> new RuntimeException("NC não encontrada com detalhes completos"));
    }

    @Transactional
    public NaoConformidade atualizarStatus(Long id, StatusNC novoStatus) {
        NaoConformidade nc = buscarPorId(id);
        nc.setStatus(novoStatus);
        return repository.save(nc);
    }

    @Transactional
    public NaoConformidade resolver(Long id, String solucao) {
        NaoConformidade nc = buscarPorId(id);
        nc.setSolucaoAdotada(solucao);
        nc.setDataResolucao(LocalDate.now());
        nc.setStatus(StatusNC.RESOLVIDA);
        return repository.save(nc);
    }

    @Transactional
    public NaoConformidade escalonar(Long id, String superior, String observacoes) {
        NaoConformidade nc = buscarPorId(id);
        
        HistoricoEscalonamento historico = new HistoricoEscalonamento();
        historico.setNaoConformidade(nc);
        historico.setSuperiorResponsavel(superior);
        historico.setDataEscalonamento(LocalDate.now());
        historico.setNovoPrazoResolucao(LocalDate.now()
            .plusDays(nc.getClassificacao().getDiasResolucao()));
        historico.setObservacoes(observacoes);
        
        nc.getHistoricoEscalonamentos().add(historico);
        nc.setNumeroEscalonamentos(nc.getNumeroEscalonamentos() + 1);
        nc.setStatus(StatusNC.ESCALONADA);
        nc.setPrazoResolucao(historico.getNovoPrazoResolucao());
        
        return repository.save(nc);
    }
}