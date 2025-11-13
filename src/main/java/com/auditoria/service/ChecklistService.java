package com.auditoria.service;

import com.auditoria.model.ItemChecklist;
import com.auditoria.repository.ItemChecklistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChecklistService {
    private final ItemChecklistRepository repository;

    public ChecklistService(ItemChecklistRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ItemChecklist criarItem(ItemChecklist item) {
        return repository.save(item);
    }

    public List<ItemChecklist> listarTodos() {
        return repository.findByAtivoTrueOrderByNumeroAsc();
    }

    public List<ItemChecklist> listarPorCategoria(String categoria) {
        return repository.findByCategoriaOrderByNumeroAsc(categoria);
    }

    public ItemChecklist buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    @Transactional
    public ItemChecklist atualizar(Long id, ItemChecklist itemAtualizado) {
        ItemChecklist item = buscarPorId(id);
        item.setDescricao(itemAtualizado.getDescricao());
        item.setCategoria(itemAtualizado.getCategoria());
        return repository.save(item);
    }

    @Transactional
    public void desativar(Long id) {
        ItemChecklist item = buscarPorId(id);
        item.setAtivo(false);
        repository.save(item);
    }
}