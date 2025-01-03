package com.saudemosaico.notificacao.service.impl;

import com.saudemosaico.notificacao.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    
    private final JavaMailSender mailSender;
    
    @Override
    public void enviar(String destinatario, String assunto, String conteudo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(conteudo, true);
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
        }
    }
}
