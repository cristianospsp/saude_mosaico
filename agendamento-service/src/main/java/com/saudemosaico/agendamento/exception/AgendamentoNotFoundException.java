package com.saudemosaico.agendamento.exception;

public class AgendamentoNotFoundException extends RuntimeException {
    public AgendamentoNotFoundException(Long id) {
        super("Agendamento não encontrado com ID: " + id);
    }
}
