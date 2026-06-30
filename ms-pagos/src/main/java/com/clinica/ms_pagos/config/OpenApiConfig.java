package com.clinica.ms_pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Pagos")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de pagos de la clínica."));
    }
}
