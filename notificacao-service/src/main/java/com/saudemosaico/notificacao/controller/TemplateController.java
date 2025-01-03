package com.saudemosaico.notificacao.controller;

import com.saudemosaico.notificacao.dto.TemplateRequest;
import com.saudemosaico.notificacao.dto.TemplateResponse;
import com.saudemosaico.notificacao.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
@Tag(name = "Templates", description = "APIs para gestão de templates de notificação")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    @Operation(summary = "Criar novo template")
    public ResponseEntity<TemplateResponse> criar(@Valid @RequestBody TemplateRequest request) {
        return ResponseEntity.ok(templateService.criar(request));
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Atualizar template existente")
    public ResponseEntity<TemplateResponse> atualizar(
            @PathVariable String codigo,
            @Valid @RequestBody TemplateRequest request) {
        return ResponseEntity.ok(templateService.atualizar(codigo, request));
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar template por código")
    public ResponseEntity<TemplateResponse> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(templateService.buscarPorCodigo(codigo));
    }

    @GetMapping
    @Operation(summary = "Listar todos os templates")
    public ResponseEntity<List<TemplateResponse>> listarTodos() {
        return ResponseEntity.ok(templateService.listarTemplates());
    }

    @PutMapping("/{codigo}/inativar")
    @Operation(summary = "Inativar template")
    public ResponseEntity<Void> inativar(@PathVariable String codigo) {
        templateService.inativar(codigo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{codigo}/reativar")
    @Operation(summary = "Reativar template")
    public ResponseEntity<Void> reativar(@PathVariable String codigo) {
        templateService.reativar(codigo);
        return ResponseEntity.ok().build();
    }
}
