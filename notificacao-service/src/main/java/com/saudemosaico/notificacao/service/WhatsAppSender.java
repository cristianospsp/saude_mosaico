package com.saudemosaico.notificacao.service;

public interface WhatsAppSender {
    void enviar(String destinatario, String mensagem);
}
