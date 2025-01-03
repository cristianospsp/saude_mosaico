package com.saudemosaico.usuarios.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuário não encontrado com ID: " + id);
    }
    
    public UsuarioNotFoundException(String cpf) {
        super("Usuário não encontrado com CPF: " + cpf);
    }
}
