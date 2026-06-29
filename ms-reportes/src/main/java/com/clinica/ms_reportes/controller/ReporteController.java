package com.clinica.ms_reportes.controller;

import com.clinica.ms_reportes.dto.ReporteDetalleDTO;
import com.clinica.ms_reportes.dto.ReporteResumenDTO;
import com.clinica.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Endpoints para la generación de reportes consolidados de la clínica")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(summary = "Obtener resumen general", description = "Genera un resumen consolidado con información general del sistema clínico.")
    @ApiResponse(responseCode = "200", description = "Resumen general generado correctamente")
    @GetMapping("/resumen")
    public ResponseEntity<ReporteResumenDTO> obtenerResumenGeneral() {
        log.info("Solicitud para generar resumen general");
        return ResponseEntity.ok(reporteService.obtenerResumenGeneral());
    }

    @Operation(summary = "Obtener reporte por paciente", description = "Genera un reporte detallado asociado a un paciente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte del paciente generado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReportePaciente(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable Long pacienteId) {
        log.info("Solicitud para generar reporte de paciente ID: {}", pacienteId);
        return ResponseEntity.ok(reporteService.obtenerReportePaciente(pacienteId));
    }

    @Operation(summary = "Obtener reporte por doctor", description = "Genera un reporte detallado asociado a un doctor específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte del doctor generado correctamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReporteDoctor(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long doctorId) {
        log.info("Solicitud para generar reporte de doctor ID: {}", doctorId);
        return ResponseEntity.ok(reporteService.obtenerReporteDoctor(doctorId));
    }

    @Operation(summary = "Obtener reporte por reserva", description = "Genera un reporte detallado asociado a una reserva médica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte de la reserva generado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ReporteDetalleDTO> obtenerReporteReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long reservaId) {
        log.info("Solicitud para generar reporte de reserva ID: {}", reservaId);
        return ResponseEntity.ok(reporteService.obtenerReporteReserva(reservaId));
    }
}
