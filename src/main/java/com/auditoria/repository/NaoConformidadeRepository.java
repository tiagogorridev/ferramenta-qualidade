package com.auditoria.repository;

import com.auditoria.model.NaoConformidade;
import com.auditoria.model.enums.StatusNC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface NaoConformidadeRepository extends JpaRepository<NaoConformidade, Long> {
    List<NaoConformidade> findByStatus(StatusNC status);
    List<NaoConformidade> findByResponsavel(String responsavel);
    List<NaoConformidade> findByPrazoResolucaoBeforeAndStatusNot(LocalDate data, StatusNC status);
    
    @Query("SELECT nc FROM NaoConformidade nc WHERE nc.status != 'RESOLVIDA' ORDER BY nc.prazoResolucao ASC")
    List<NaoConformidade> findNaoConformidadesAbertas();
}
