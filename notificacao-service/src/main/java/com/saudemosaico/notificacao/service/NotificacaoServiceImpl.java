package com.saudemosaico.notificacao.service;

import com.saudemosaico.notificacao.domain.Notificacao;
import com.saudemosaico.notificacao.domain.StatusNotificacao;
import com.saudemosaico.notificacao.domain.Template;
import com.saudemosaico.notificacao.dto.NotificacaoRequest;
import com.saudemosaico.notificacao.dto.NotificacaoResponse;
import com.saudemosaico.notificacao.exception.NotificacaoException;
import com.saudemosaico.notificacao.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacaoServiceImpl implements NotificacaoService {
    
    private final NotificacaoRepository notificacaoRepository;
    private final TemplateService templateService;
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final WhatsAppSender whatsAppSender;
    
    @Override
    @Transactional
    public NotificacaoResponse enviar(NotificacaoRequest request) {
        Template template = templateService.buscarTemplate(request.getCodigoTemplate());
        
        String conteudoProcessado = processarTemplate(template, request.getParametros());
        
        Notificacao notificacao = new Notificacao();
        notificacao.setDestinatario(request.getDestinatario());
        notificacao.setTitulo(template.getAssunto());
        notificacao.setConteudo(conteudoProcessado);
        notificacao.setTipo(request.getTipo());
        
        try {
            switch (request.getTipo()) {
                case EMAIL:
                    emailSender.enviar(request.getDestinatario(), template.getAssunto(), conteudoProcessado);
                    break;
                case SMS:
                    smsSender.enviar(request.getDestinatario(), conteudoProcessado);
                    break;
                case WHATSAPP:
                    whatsAppSender.enviar(request.getDestinatario(), conteudoProcessado);
                    break;
            }
            
            notificacao.setStatus(StatusNotificacao.ENVIADA);
            notificacao.setDataEnvio(LocalDateTime.now());
            
        } catch (Exception e) {
            notificacao.setStatus(StatusNotificacao.FALHA);
            notificacao.setErro(e.getMessage());
        }
        
        return converterParaResponse(notificacaoRepository.save(notificacao));
    }
    
    @Override
    @Transactional(readOnly = true)
    public NotificacaoResponse buscarPorId(Long id) {
        return notificacaoRepository.findById(id)
            .map(this::converterParaResponse)
            .orElseThrow(() -> new NotificacaoException("Notificação não encontrada"));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacaoResponse> listarNotificacoes() {
        return notificacaoRepository.findAll().stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacaoResponse> listarNotificacoesPendentes() {
        return notificacaoRepository.findByStatus(StatusNotificacao.PENDENTE).stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void reprocessarNotificacoesFalhas() {
        List<Notificacao> falhas = notificacaoRepository.findByStatus(StatusNotificacao.FALHA);
        for (Notificacao notificacao : falhas) {
            try {
                switch (notificacao.getTipo()) {
                    case EMAIL:
                        emailSender.enviar(notificacao.getDestinatario(), 
                                         notificacao.getTitulo(), 
                                         notificacao.getConteudo());
                        break;
                    case SMS:
                        smsSender.enviar(notificacao.getDestinatario(), 
                                       notificacao.getConteudo());
                        break;
                    case WHATSAPP:
                        whatsAppSender.enviar(notificacao.getDestinatario(), 
                                            notificacao.getConteudo());
                        break;
                }
                
                notificacao.setStatus(StatusNotificacao.ENVIADA);
                notificacao.setDataEnvio(LocalDateTime.now());
                notificacao.setErro(null);
                
            } catch (Exception e) {
                notificacao.setErro(e.getMessage());
            }
            
            notificacaoRepository.save(notificacao);
        }
    }
    
    private NotificacaoResponse converterParaResponse(Notificacao notificacao) {
        NotificacaoResponse response = new NotificacaoResponse();
        response.setId(notificacao.getId());
        response.setDestinatario(notificacao.getDestinatario());
        response.setTitulo(notificacao.getTitulo());
        response.setConteudo(notificacao.getConteudo());
        response.setTipo(notificacao.getTipo());
        response.setStatus(notificacao.getStatus());
        response.setErro(notificacao.getErro());
        response.setDataEnvio(notificacao.getDataEnvio());
        response.setCriadoEm(notificacao.getCriadoEm());
        response.setAtualizadoEm(notificacao.getAtualizadoEm());
        return response;
    }
    
    private String processarTemplate(Template template, Map<String, String> parametros) {
        String conteudo = template.getConteudo();
        if (parametros != null) {
            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                conteudo = conteudo.replace("${" + entry.getKey() + "}", entry.getValue());
            }
        }
        return conteudo;
    }
}
