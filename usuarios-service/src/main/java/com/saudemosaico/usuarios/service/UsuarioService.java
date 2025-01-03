package com.saudemosaico.usuarios.service;

import com.saudemosaico.usuarios.dto.UsuarioRequest;
import com.saudemosaico.usuarios.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
    UsuarioResponse criar(UsuarioRequest request);
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse buscarPorCpf(String cpf);
    List<UsuarioResponse> listarTodos();
    List<UsuarioResponse> listarAtivos();
    UsuarioResponse atualizar(Long id, UsuarioRequest request);
    void inativar(Long id);
    void reativar(Long id);
}
