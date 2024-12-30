package com.saudemosaico.especialistas.exception;

public class EspecialistaNaoEncontradoException extends RuntimeException {
    public EspecialistaNaoEncontradoException(Long id) {
        super("Especialista não encontrado com ID: " + id);
    }
    
    public EspecialistaNaoEncontradoException(String crm) {
        super("Especialista não encontrado com CRM: " + crm);
    }
}
