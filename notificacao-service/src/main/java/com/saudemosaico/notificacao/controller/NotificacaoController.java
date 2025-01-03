package com.saudemosaico.notificacao.controller;

import com.saudemosaico.notificacao.dto.NotificacaoRequest;
import com.saudemosaico.notificacao.dto.NotificacaoResponse;
import com.saudemosaico.notificacao.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacoes")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "APIs para envio e controle de notificações")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    @Operation(summary = "Enviar nova notificação")
    public ResponseEntity<NotificacaoResponse> enviar(@Valid @RequestBody NotificacaoRequest request) {
        return ResponseEntity.ok(notificacaoService.enviar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificação por ID")
    public ResponseEntity<NotificacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as notificações")
    public ResponseEntity<List<NotificacaoResponse>> listarTodas() {
        return ResponseEntity.ok(notificacaoService.listarNotificacoes());
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar notificações pendentes")
    public ResponseEntity<List<NotificacaoResponse>> listarPendentes() {
        return ResponseEntity.ok(notificacaoService.listarNotificacoesPendentes());
    }

    @PostMapping("/reprocessar-falhas")
    @Operation(summary = "Reprocessar notificações com falha")
    public ResponseEntity<Void> reprocessarFalhas() {
        notificacaoService.reprocessarNotificacoesFalhas();
        return ResponseEntity.ok().build();
    }
}
