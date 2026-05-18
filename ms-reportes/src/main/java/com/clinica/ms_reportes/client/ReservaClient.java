package com.clinica.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas")
    List<Object> listarReservas();

    @GetMapping("/api/v1/reservas/{id}")
    Object buscarReservaPorId(@PathVariable("id") Long id);
}
