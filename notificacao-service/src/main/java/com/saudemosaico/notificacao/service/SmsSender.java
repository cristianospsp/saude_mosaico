package com.saudemosaico.notificacao.service;

public interface SmsSender {
    void enviar(String destinatario, String mensagem);
}
