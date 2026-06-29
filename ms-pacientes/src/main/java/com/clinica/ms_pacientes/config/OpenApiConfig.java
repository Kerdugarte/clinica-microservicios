package com.clinica.ms_pacientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pacientesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Pacientes")
                        .version("1.0.0")
                        .description("Documentación de endpoints para la gestión de pacientes de la clínica."));
    }
}
