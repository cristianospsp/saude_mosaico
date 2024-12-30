package com.saudemosaico.agendamento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Agendamentos - Saúde Mosaico")
                        .description("API para gerenciamento de agendamentos de consultas")
                        .version("1.0"));
    }
}
