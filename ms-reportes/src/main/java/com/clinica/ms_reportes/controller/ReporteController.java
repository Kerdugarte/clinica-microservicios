package com.clinica.ms_reportes.controller;

import com.clinica.ms_reportes.dto.ReporteDetalleDTO;
import com.clinica.ms_reportes.dto.ReporteResumenDTO;
import com.clinica.ms_reportes.service.ReporteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/resumen")
    public ResponseEntity<ReporteResumenDTO> obtenerResumenGeneral() {
        log.info("Solicitud para generar resumen general");
        return ResponseEntity.ok(reporteService.obtenerResumenGeneral());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReportePaciente(@PathVariable Long pacienteId) {
        log.info("Solicitud para generar reporte de paciente ID: {}", pacienteId);
        return ResponseEntity.ok(reporteService.obtenerReportePaciente(pacienteId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReporteDoctor(@PathVariable Long doctorId) {
        log.info("Solicitud para generar reporte de doctor ID: {}", doctorId);
        return ResponseEntity.ok(reporteService.obtenerReporteDoctor(doctorId));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReporteReserva(@PathVariable Long reservaId) {
        log.info("Solicitud para generar reporte de reserva ID: {}", reservaId);
        return ResponseEntity.ok(reporteService.obtenerReporteReserva(reservaId));
    }
}
