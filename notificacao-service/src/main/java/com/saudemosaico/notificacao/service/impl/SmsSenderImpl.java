package com.saudemosaico.notificacao.service.impl;

import com.saudemosaico.notificacao.service.SmsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsSenderImpl implements SmsSender {
    
    @Override
    public void enviar(String destinatario, String mensagem) {
        // TODO: Implementar integração com serviço de SMS (ex: Twilio)
        log.info("Simulando envio de SMS para {}: {}", destinatario, mensagem);
    }
}
