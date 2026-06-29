package com.clinica.auth_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI authOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Autenticación")
                        .version("1.0.0")
                        .description("Documentación de endpoints para autenticación, login y validación de JWT del sistema clínico."));
    }
}
