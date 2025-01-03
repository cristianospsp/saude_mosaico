package com.saudemosaico.notificacao.service.impl;

import com.saudemosaico.notificacao.service.WhatsAppSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WhatsAppSenderImpl implements WhatsAppSender {
    
    @Override
    public void enviar(String destinatario, String mensagem) {
        // TODO: Implementar integração com WhatsApp Business API
        log.info("Simulando envio de WhatsApp para {}: {}", destinatario, mensagem);
    }
}
