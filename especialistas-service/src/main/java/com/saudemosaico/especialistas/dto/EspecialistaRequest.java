package com.saudemosaico.especialistas.dto;

import com.saudemosaico.especialistas.domain.Especialidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class EspecialistaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "CRM é obrigatório")
    private String crm;
    
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    private String telefone;
    
    @NotEmpty(message = "Pelo menos uma especialidade deve ser informada")
    private Set<Especialidade> especialidades;
}
