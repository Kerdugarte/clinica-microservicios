package com.clinica.ms_historial.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI historialOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Historial Clínico")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión del historial clínico de pacientes."));
    }
}
