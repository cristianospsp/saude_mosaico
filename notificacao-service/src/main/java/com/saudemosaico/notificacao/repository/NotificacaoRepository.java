package com.saudemosaico.notificacao.repository;

import com.saudemosaico.notificacao.domain.Notificacao;
import com.saudemosaico.notificacao.domain.StatusNotificacao;
import com.saudemosaico.notificacao.domain.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByStatus(StatusNotificacao status);
    List<Notificacao> findByTipoAndStatus(TipoNotificacao tipo, StatusNotificacao status);
    List<Notificacao> findByDestinatarioAndCriadoEmBetween(
        String destinatario, 
        LocalDateTime inicio, 
        LocalDateTime fim
    );
}
