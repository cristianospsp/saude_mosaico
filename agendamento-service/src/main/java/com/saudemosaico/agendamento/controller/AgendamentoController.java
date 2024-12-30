package com.saudemosaico.agendamento.controller;

import com.saudemosaico.agendamento.dto.AgendamentoRequest;
import com.saudemosaico.agendamento.dto.AgendamentoResponse;
import com.saudemosaico.agendamento.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamento", description = "APIs para gerenciamento de agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    @Operation(summary = "Criar novo agendamento")
    public ResponseEntity<AgendamentoResponse> criar(@Valid @RequestBody AgendamentoRequest request) {
        return new ResponseEntity<>(agendamentoService.criar(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os agendamentos")
    public ResponseEntity<List<AgendamentoResponse>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar agendamentos por paciente")
    public ResponseEntity<List<AgendamentoResponse>> listarPorPaciente(@PathVariable String pacienteId) {
        return ResponseEntity.ok(agendamentoService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/especialista/{especialistaId}")
    @Operation(summary = "Listar agendamentos por especialista")
    public ResponseEntity<List<AgendamentoResponse>> listarPorEspecialista(@PathVariable String especialistaId) {
        return ResponseEntity.ok(agendamentoService.listarPorEspecialista(especialistaId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agendamento existente")
    public ResponseEntity<AgendamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequest request) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar agendamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
