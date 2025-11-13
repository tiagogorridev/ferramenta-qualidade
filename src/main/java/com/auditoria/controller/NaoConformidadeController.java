package com.auditoria.controller;

import com.auditoria.model.NaoConformidade;
import com.auditoria.model.enums.StatusNC;
import com.auditoria.service.NaoConformidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nao-conformidades")
@CrossOrigin(origins = "*")
public class NaoConformidadeController {
    private final NaoConformidadeService service;

    public NaoConformidadeController(NaoConformidadeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NaoConformidade> criar(@RequestBody NaoConformidade nc) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(nc));
    }

    @GetMapping
    public ResponseEntity<List<NaoConformidade>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/abertas")
    public ResponseEntity<List<NaoConformidade>> listarAbertas() {
        return ResponseEntity.ok(service.listarAbertas());
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<NaoConformidade>> listarAtrasadas() {
        return ResponseEntity.ok(service.listarAtrasadas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NaoConformidade> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<NaoConformidade>> listarPorResponsavel(
            @PathVariable String responsavel) {
        return ResponseEntity.ok(service.listarPorResponsavel(responsavel));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<NaoConformidade> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        StatusNC status = StatusNC.valueOf(payload.get("status"));
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<NaoConformidade> resolver(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(service.resolver(id, payload.get("solucao")));
    }

    @PostMapping("/{id}/escalonar")
    public ResponseEntity<NaoConformidade> escalonar(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(service.escalonar(
            id,
            payload.get("superior"),
            payload.get("observacoes")
        ));
    }
}