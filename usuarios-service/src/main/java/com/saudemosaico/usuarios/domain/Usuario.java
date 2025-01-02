package com.saudemosaico.usuarios.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @Column(unique = true)
    private String rg;
    
    @Column(nullable = false)
    private LocalDate dataNascimento;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String telefone;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSanguineo tipoSanguineo;
    
    @ElementCollection
    @CollectionTable(name = "alergias")
    private Set<String> alergias;
    
    @ElementCollection
    @CollectionTable(name = "medicamentos")
    private Set<String> medicamentosEmUso;
    
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
