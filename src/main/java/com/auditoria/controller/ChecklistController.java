package com.auditoria.controller;

import com.auditoria.model.ItemChecklist;
import com.auditoria.service.ChecklistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@CrossOrigin(origins = "*")
public class ChecklistController {
    private final ChecklistService service;

    public ChecklistController(ChecklistService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ItemChecklist> criar(@RequestBody ItemChecklist item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarItem(item));
    }

    @GetMapping
    public ResponseEntity<List<ItemChecklist>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemChecklist> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ItemChecklist>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.listarPorCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemChecklist> atualizar(
            @PathVariable Long id,
            @RequestBody ItemChecklist item) {
        return ResponseEntity.ok(service.atualizar(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}