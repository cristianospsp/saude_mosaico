package com.saudemosaico.usuarios.dto;

import lombok.Data;

@Data
public class EnderecoResponse {
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private boolean principal;
}
