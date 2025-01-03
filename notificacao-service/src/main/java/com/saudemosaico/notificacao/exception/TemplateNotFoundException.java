package com.saudemosaico.notificacao.exception;

public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(String codigo) {
        super("Template não encontrado com código: " + codigo);
    }
}
