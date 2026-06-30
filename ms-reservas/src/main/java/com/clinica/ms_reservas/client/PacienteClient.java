package com.clinica.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pacientes")
public interface PacienteClient {

    @GetMapping("/api/v1/pacientes/{id}")
    Object buscarPacientePorId(@PathVariable("id") Long id);
}
