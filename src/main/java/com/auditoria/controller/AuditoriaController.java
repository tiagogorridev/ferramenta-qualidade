package com.auditoria.controller;

import com.auditoria.model.Auditoria;
import com.auditoria.model.RespostaChecklist;
import com.auditoria.service.AuditoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
@CrossOrigin(origins = "*")
public class AuditoriaController {
    private final AuditoriaService service;

    public AuditoriaController(AuditoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Auditoria> criar(@RequestBody Auditoria auditoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(auditoria));
    }

    @GetMapping
    public ResponseEntity<List<Auditoria>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/{id}/respostas")
    public ResponseEntity<Auditoria> adicionarResposta(
            @PathVariable Long id,
            @RequestBody RespostaChecklist resposta) {
        return ResponseEntity.ok(service.adicionarResposta(id, resposta));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Auditoria> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarAuditoria(id));
    }

    @GetMapping("/projeto/{nomeProjeto}")
    public ResponseEntity<List<Auditoria>> buscarPorProjeto(@PathVariable String nomeProjeto) {
        return ResponseEntity.ok(service.buscarPorProjeto(nomeProjeto));
    }

    @GetMapping("/auditor/{auditor}")
    public ResponseEntity<List<Auditoria>> buscarPorAuditor(@PathVariable String auditor) {
        return ResponseEntity.ok(service.buscarPorAuditor(auditor));
    }
}