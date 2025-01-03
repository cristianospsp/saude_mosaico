package com.saudemosaico.notificacao.service;

import com.saudemosaico.notificacao.dto.NotificacaoRequest;
import com.saudemosaico.notificacao.dto.NotificacaoResponse;
import java.util.List;

public interface NotificacaoService {
    NotificacaoResponse enviar(NotificacaoRequest request);
    NotificacaoResponse buscarPorId(Long id);
    List<NotificacaoResponse> listarNotificacoes();
    List<NotificacaoResponse> listarNotificacoesPendentes();
    void reprocessarNotificacoesFalhas();
}
