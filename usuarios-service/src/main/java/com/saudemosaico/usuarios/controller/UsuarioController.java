package com.saudemosaico.usuarios.controller;

import com.saudemosaico.usuarios.dto.UsuarioRequest;
import com.saudemosaico.usuarios.dto.UsuarioResponse;
import com.saudemosaico.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "APIs para gerenciamento de usuários/pacientes")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
        return new ResponseEntity<>(usuarioService.criar(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar usuário por CPF")
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.buscarPorCpf(cpf));
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar usuários ativos")
    public ResponseEntity<List<UsuarioResponse>> listarAtivos() {
        return ResponseEntity.ok(usuarioService.listarAtivos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.atualizar(id, request));
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar usuário")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reativar")
    @Operation(summary = "Reativar usuário")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        usuarioService.reativar(id);
        return ResponseEntity.noContent().build();
    }
}
