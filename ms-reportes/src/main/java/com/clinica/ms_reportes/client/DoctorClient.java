package com.clinica.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-doctores")
public interface DoctorClient {

    @GetMapping("/api/v1/doctores")
    List<Object> listarDoctores();

    @GetMapping("/api/v1/doctores/{id}")
    Object buscarDoctorPorId(@PathVariable("id") Long id);
}
