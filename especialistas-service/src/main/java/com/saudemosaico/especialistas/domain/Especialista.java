package com.saudemosaico.especialistas.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "especialistas")
public class Especialista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String crm;
    
    @Column(nullable = false)
    private String cpf;
    
    @Column(nullable = false)
    private String email;
    
    private String telefone;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "especialista_especialidades")
    @Column(name = "especialidade")
    private Set<Especialidade> especialidades;
    
    @Column(nullable = false)
    private boolean ativo = true;
    
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
