package com.clinica.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-historial")
public interface HistorialClient {

    @GetMapping("/api/v1/historiales")
    List<Object> listarHistoriales();

    @GetMapping("/api/v1/historiales/{id}")
    Object buscarHistorialPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/historiales/paciente/{pacienteId}")
    List<Object> buscarHistorialesPorPaciente(@PathVariable("pacienteId") Long pacienteId);

    @GetMapping("/api/v1/historiales/doctor/{doctorId}")
    List<Object> buscarHistorialesPorDoctor(@PathVariable("doctorId") Long doctorId);

    @GetMapping("/api/v1/historiales/reserva/{reservaId}")
    List<Object> buscarHistorialesPorReserva(@PathVariable("reservaId") Long reservaId);
}
