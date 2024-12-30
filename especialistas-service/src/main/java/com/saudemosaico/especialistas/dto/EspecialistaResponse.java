package com.saudemosaico.especialistas.dto;

import com.saudemosaico.especialistas.domain.Especialidade;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EspecialistaResponse {
    private Long id;
    private String nome;
    private String crm;
    private String email;
    private String telefone;
    private Set<Especialidade> especialidades;
    private boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
