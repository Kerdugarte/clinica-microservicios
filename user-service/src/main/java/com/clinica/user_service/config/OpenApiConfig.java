package com.clinica.user_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Usuarios")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de usuarios del sistema clínico."));
    }
}
