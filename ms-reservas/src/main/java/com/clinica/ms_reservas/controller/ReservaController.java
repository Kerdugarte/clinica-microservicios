package com.clinica.ms_reservas.controller;

import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;
import com.clinica.ms_reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Endpoints para la gestión de reservas médicas de la clínica")
public class ReservaController {

    private static final Logger log = LoggerFactory.getLogger(ReservaController.class);

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva médica asociada a un paciente y un doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o reserva no disponible"),
            @ApiResponse(responseCode = "404", description = "Paciente o doctor no encontrado")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaRequestDTO requestDTO) {
        log.info("Solicitud para crear reserva. Paciente ID: {}, Doctor ID: {}", requestDTO.getPacienteId(), requestDTO.getDoctorId());
        ReservaResponseDTO response = reservaService.crearReserva(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar reservas", description = "Obtiene el listado completo de reservas médicas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        log.info("Solicitud para listar reservas");
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @Operation(summary = "Buscar reserva por ID", description = "Obtiene la información de una reserva médica mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaPorId(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para buscar reserva por ID: {}", id);
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @Operation(summary = "Buscar reservas por paciente", description = "Obtiene todas las reservas asociadas a un paciente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas del paciente obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorPaciente(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable Long pacienteId) {
        log.info("Solicitud para buscar reservas por paciente ID: {}", pacienteId);
        return ResponseEntity.ok(reservaService.buscarReservasPorPaciente(pacienteId));
    }

    @Operation(summary = "Buscar reservas por doctor", description = "Obtiene todas las reservas asociadas a un doctor específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas del doctor obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorDoctor(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long doctorId) {
        log.info("Solicitud para buscar reservas por doctor ID: {}", doctorId);
        return ResponseEntity.ok(reservaService.buscarReservasPorDoctor(doctorId));
    }

    @Operation(summary = "Buscar reservas por estado", description = "Obtiene las reservas filtradas por estado.")
    @ApiResponse(responseCode = "200", description = "Reservas filtradas correctamente")
    @GetMapping("/estado")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorEstado(
            @Parameter(description = "Estado de la reserva", example = "PENDIENTE")
            @RequestParam String estado) {
        log.info("Solicitud para buscar reservas por estado: {}", estado);
        return ResponseEntity.ok(reservaService.buscarReservasPorEstado(estado));
    }

    @Operation(summary = "Actualizar reserva", description = "Actualiza los datos de una reserva médica existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {
        log.info("Solicitud para actualizar reserva con ID: {}", id);
        return ResponseEntity.ok(reservaService.actualizarReserva(id, requestDTO));
    }

    @Operation(summary = "Cambiar estado de reserva", description = "Actualiza únicamente el estado de una reserva médica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstadoReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la reserva", example = "CONFIRMADA")
            @RequestParam String estado) {
        log.info("Solicitud para cambiar estado de reserva ID: {} a {}", id, estado);
        return ResponseEntity.ok(reservaService.cambiarEstadoReserva(id, estado));
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina lógicamente una reserva médica mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente reserva con ID: {}", id);
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
