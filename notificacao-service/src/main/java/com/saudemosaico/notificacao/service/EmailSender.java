package com.saudemosaico.notificacao.service;

public interface EmailSender {
    void enviar(String destinatario, String assunto, String conteudo);
}
