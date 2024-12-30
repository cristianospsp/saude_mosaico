package com.saudemosaico.agendamento.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agendamentos")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String pacienteId;
    
    @Column(nullable = false)
    private String especialistaId;
    
    @Column(nullable = false)
    private String especialidade;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraAgendamento;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;
    
    private String linkVideoConferencia;
    
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
