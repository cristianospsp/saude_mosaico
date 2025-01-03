package com.saudemosaico.notificacao.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificacoes")
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String destinatario;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false, length = 2000)
    private String conteudo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status = StatusNotificacao.PENDENTE;
    
    private String erro;
    
    private LocalDateTime dataEnvio;
    
    @Column(updatable = false)
    private LocalDateTime criadoEm;
    
    private LocalDateTime atualizadoEm;
    
    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
