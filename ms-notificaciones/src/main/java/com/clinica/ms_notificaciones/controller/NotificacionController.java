package com.clinica.ms_notificaciones.controller;

import com.clinica.ms_notificaciones.dto.NotificacionRequestDTO;
import com.clinica.ms_notificaciones.dto.NotificacionResponseDTO;
import com.clinica.ms_notificaciones.service.NotificacionService;
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
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody NotificacionRequestDTO requestDTO) {
        log.info("Solicitud para crear notificacion de reserva ID: {}", requestDTO.getReservaId());
        NotificacionResponseDTO response = notificacionService.crearNotificacion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarNotificaciones() {
        log.info("Solicitud para listar notificaciones");
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarNotificacionPorId(@PathVariable Long id) {
        log.info("Solicitud para buscar notificacion por ID: {}", id);
        return ResponseEntity.ok(notificacionService.buscarNotificacionPorId(id));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorReserva(@PathVariable Long reservaId) {
        log.info("Solicitud para buscar notificaciones por reserva ID: {}", reservaId);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorReserva(reservaId));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorEstado(@RequestParam String estado) {
        log.info("Solicitud para buscar notificaciones por estado: {}", estado);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorEstado(estado));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarNotificacionesPorTipo(@RequestParam String tipoNotificacion) {
        log.info("Solicitud para buscar notificaciones por tipo: {}", tipoNotificacion);
        return ResponseEntity.ok(notificacionService.buscarNotificacionesPorTipo(tipoNotificacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizarNotificacion(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequestDTO requestDTO) {
        log.info("Solicitud para actualizar notificacion con ID: {}", id);
        return ResponseEntity.ok(notificacionService.actualizarNotificacion(id, requestDTO));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<NotificacionResponseDTO> cambiarEstadoNotificacion(
            @PathVariable Long id,
            @RequestParam String estado) {
        log.info("Solicitud para cambiar estado de notificacion ID: {} a {}", id, estado);
        return ResponseEntity.ok(notificacionService.cambiarEstadoNotificacion(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente notificacion con ID: {}", id);
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
