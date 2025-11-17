package com.auditoria.repository;

import com.auditoria.model.NaoConformidade;
import com.auditoria.model.enums.StatusNC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NaoConformidadeRepository extends JpaRepository<NaoConformidade, Long> {
    List<NaoConformidade> findByStatus(StatusNC status);
    List<NaoConformidade> findByResponsavel(String responsavel);
    List<NaoConformidade> findByPrazoResolucaoBeforeAndStatusNot(LocalDate data, StatusNC status);
    
    @Query("SELECT nc FROM NaoConformidade nc " +
       "LEFT JOIN FETCH nc.respostaChecklist rc " +
       "LEFT JOIN FETCH rc.auditoria " +
       "LEFT JOIN FETCH rc.itemChecklist " +
       "WHERE nc.status != 'RESOLVIDA' " +
       "ORDER BY nc.prazoResolucao ASC")
    List<NaoConformidade> findNaoConformidadesAbertas();

    @Query("SELECT nc FROM NaoConformidade nc " +
           "LEFT JOIN FETCH nc.respostaChecklist rc " +
           "LEFT JOIN FETCH rc.auditoria " +
           "LEFT JOIN FETCH rc.itemChecklist " +
           "WHERE nc.id = :id")
    Optional<NaoConformidade> findByIdCompletamente(@Param("id") Long id);
}
