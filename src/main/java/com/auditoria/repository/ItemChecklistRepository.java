package com.auditoria.repository;

import com.auditoria.model.ItemChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemChecklistRepository extends JpaRepository<ItemChecklist, Long> {
    List<ItemChecklist> findByAtivoTrueOrderByNumeroAsc();
    List<ItemChecklist> findByCategoriaOrderByNumeroAsc(String categoria);
}
