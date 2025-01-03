package com.saudemosaico.usuarios.dto;

import com.saudemosaico.usuarios.domain.TipoSanguineo;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private Set<EnderecoResponse> enderecos;
    private TipoSanguineo tipoSanguineo;
    private Set<String> alergias;
    private Set<String> medicamentosEmUso;
    private boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
