package com.clinica.ms_doctores.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI doctoresOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Doctores")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de doctores de la clínica."));
    }
}
