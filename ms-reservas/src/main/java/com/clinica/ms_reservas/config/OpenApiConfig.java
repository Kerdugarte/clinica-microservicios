package com.clinica.ms_reservas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reservasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Reservas")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de reservas médicas de la clínica."));
    }
}
