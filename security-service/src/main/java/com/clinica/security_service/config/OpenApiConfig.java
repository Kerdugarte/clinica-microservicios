package com.clinica.security_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI securityOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Seguridad")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de roles, permisos y autorización del sistema clínico."));
    }
}
