package com.auditoria.repository;

import com.auditoria.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByNomeProjetoContainingIgnoreCase(String nomeProjeto);
    List<Auditoria> findByAuditor(String auditor);
    List<Auditoria> findByDataAuditoriaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Auditoria> findByFinalizadaOrderByDataAuditoriaDesc(Boolean finalizada);
}

