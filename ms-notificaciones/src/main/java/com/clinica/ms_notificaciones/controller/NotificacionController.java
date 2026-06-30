package com.clinica.ms_notificaciones.controller;

import com.clinica.ms_notificaciones.dto.NotificacionRequestDTO;
import com.clinica.ms_notificaciones.dto.NotificacionResponseDTO;
import com.clinica.ms_notificaciones.service.NotificacionService;
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
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Endpoints para la gestión de notificaciones de la clínica")
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación asociada a una reserva médica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody NotificacionRequestDTO requestDTO) {
        log.info("Solicitud para crear notificacion de reserva ID: {}", requestDTO.getReservaId());
        NotificacionResponseDTO response = notificacionService.crearNotificacion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar notificaciones", description = "Obtiene el listado completo de notificaciones registradas.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        log.info("Solicitud para listar notificaciones");
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }

    @Operation(summary = "Buscar notificación por ID", description = "Obtiene la información de una notificación mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarNotificacionPorId(
            @Parameter(description = "Identificador de la notificación", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para buscar notificacion por ID: {}", id);
        return ResponseEntity.ok(notificacionService.buscarNotificacionPorId(id));
    }

    @Operation(summary = "Buscar notificaciones por reserva", description = "Obtiene todas las notificaciones asociadas a una reserva médica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones de la reserva obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long reservaId) {
        log.info("Solicitud para buscar notificaciones por reserva ID: {}", reservaId);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorReserva(reservaId));
    }

    @Operation(summary = "Buscar notificaciones por estado", description = "Obtiene las notificaciones filtradas por estado.")
    @ApiResponse(responseCode = "200", description = "Notificaciones filtradas correctamente")
    @GetMapping("/estado")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorEstado(
            @Parameter(description = "Estado de la notificación", example = "PENDIENTE")
            @RequestParam String estado) {
        log.info("Solicitud para buscar notificaciones por estado: {}", estado);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorEstado(estado));
    }

    @Operation(summary = "Buscar notificaciones por tipo", description = "Obtiene las notificaciones filtradas por tipo de notificación.")
    @ApiResponse(responseCode = "200", description = "Notificaciones filtradas correctamente")
    @GetMapping("/tipo")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorTipo(
            @Parameter(description = "Tipo de notificación", example = "EMAIL")
            @RequestParam String tipoNotificacion) {
        log.info("Solicitud para buscar notificaciones por tipo: {}", tipoNotificacion);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorTipo(tipoNotificacion));
    }

    @Operation(summary = "Actualizar notificación", description = "Actualiza los datos de una notificación existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizarNotificacion(
            @Parameter(description = "Identificador de la notificación", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequestDTO requestDTO) {
        log.info("Solicitud para actualizar notificacion con ID: {}", id);
        return ResponseEntity.ok(notificacionService.actualizarNotificacion(id, requestDTO));
    }

    @Operation(summary = "Cambiar estado de notificación", description = "Actualiza únicamente el estado de una notificación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<NotificacionResponseDTO> cambiarEstadoNotificacion(
            @Parameter(description = "Identificador de la notificación", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la notificación", example = "ENVIADA")
            @RequestParam String estado) {
        log.info("Solicitud para cambiar estado de notificacion ID: {} a {}", id, estado);
        return ResponseEntity.ok(notificacionService.cambiarEstadoNotificacion(id, estado));
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina lógicamente una notificación mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(
            @Parameter(description = "Identificador de la notificación", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente notificacion con ID: {}", id);
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
