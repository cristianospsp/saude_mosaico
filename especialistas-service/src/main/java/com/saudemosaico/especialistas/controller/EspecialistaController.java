package com.saudemosaico.especialistas.controller;

import com.saudemosaico.especialistas.domain.Especialidade;
import com.saudemosaico.especialistas.dto.EspecialistaRequest;
import com.saudemosaico.especialistas.dto.EspecialistaResponse;
import com.saudemosaico.especialistas.service.EspecialistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especialistas")
@RequiredArgsConstructor
@Tag(name = "Especialistas", description = "APIs para gerenciamento de especialistas")
public class EspecialistaController {

    private final EspecialistaService especialistaService;

    @PostMapping
    @Operation(summary = "Cadastrar novo especialista")
    public ResponseEntity<EspecialistaResponse> criar(@Valid @RequestBody EspecialistaRequest request) {
        return new ResponseEntity<>(especialistaService.criar(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar especialista por ID")
    public ResponseEntity<EspecialistaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialistaService.buscarPorId(id));
    }

    @GetMapping("/crm/{crm}")
    @Operation(summary = "Buscar especialista por CRM")
    public ResponseEntity<EspecialistaResponse> buscarPorCrm(@PathVariable String crm) {
        return ResponseEntity.ok(especialistaService.buscarPorCrm(crm));
    }

    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Listar especialistas por especialidade")
    public ResponseEntity<List<EspecialistaResponse>> buscarPorEspecialidade(
            @PathVariable Especialidade especialidade) {
        return ResponseEntity.ok(especialistaService.buscarPorEspecialidade(especialidade));
    }

    @GetMapping
    @Operation(summary = "Listar todos os especialistas")
    public ResponseEntity<List<EspecialistaResponse>> listarTodos() {
        return ResponseEntity.ok(especialistaService.listarTodos());
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar especialistas ativos")
    public ResponseEntity<List<EspecialistaResponse>> listarAtivos() {
        return ResponseEntity.ok(especialistaService.listarAtivos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar especialista")
    public ResponseEntity<EspecialistaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecialistaRequest request) {
        return ResponseEntity.ok(especialistaService.atualizar(id, request));
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar especialista")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        especialistaService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Operation(summary = "Reativar especialista")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        especialistaService.reativar(id);
        return ResponseEntity.noContent().build();
    }
}
