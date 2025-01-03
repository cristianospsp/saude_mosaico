package com.saudemosaico.usuarios.dto;

import com.saudemosaico.usuarios.domain.TipoSanguineo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UsuarioRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    
    @Pattern(regexp = "\\d{7,9}", message = "RG deve conter entre 7 e 9 dígitos")
    private String rg;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;
    
    @Valid
    private Set<EnderecoRequest> enderecos;
    
    @NotNull(message = "Tipo sanguíneo é obrigatório")
    private TipoSanguineo tipoSanguineo;
    
    private Set<String> alergias;
    
    private Set<String> medicamentosEmUso;
}
