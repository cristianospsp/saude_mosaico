package com.saudemosaico.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoRequest {
    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;
    
    @NotBlank(message = "Número é obrigatório")
    private String numero;
    
    private String complemento;
    
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;
    
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;
    
    @NotBlank(message = "Estado é obrigatório")
    private String estado;
    
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String cep;
    
    private boolean principal = false;
}
