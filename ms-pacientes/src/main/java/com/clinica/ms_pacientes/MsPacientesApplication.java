package com.clinica.ms_pacientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsPacientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPacientesApplication.class, args);
    }

}
