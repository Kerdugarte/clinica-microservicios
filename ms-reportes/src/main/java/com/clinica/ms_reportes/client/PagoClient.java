package com.clinica.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @GetMapping("/api/v1/pagos")
    List<Object> listarPagos();

    @GetMapping("/api/v1/pagos/{id}")
    Object buscarPagoPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/pagos/reserva/{reservaId}")
    List<Object> buscarPagosPorReserva(@PathVariable("reservaId") Long reservaId);
}
