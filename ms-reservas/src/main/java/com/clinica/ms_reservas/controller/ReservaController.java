package com.clinica.ms_reservas.controller;

import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;
import com.clinica.ms_reservas.service.ReservaService;
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
public class ReservaController {

    private static final Logger log = LoggerFactory.getLogger(ReservaController.class);

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaRequestDTO requestDTO) {
        log.info("Solicitud para crear reserva. Paciente ID: {}, Doctor ID: {}", requestDTO.getPacienteId(), requestDTO.getDoctorId());
        ReservaResponseDTO response = reservaService.crearReserva(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        log.info("Solicitud para listar reservas");
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaPorId(@PathVariable Long id) {
        log.info("Solicitud para buscar reserva por ID: {}", id);
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorPaciente(@PathVariable Long pacienteId) {
        log.info("Solicitud para buscar reservas por paciente ID: {}", pacienteId);
        return ResponseEntity.ok(reservaService.buscarReservasPorPaciente(pacienteId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorDoctor(@PathVariable Long doctorId) {
        log.info("Solicitud para buscar reservas por doctor ID: {}", doctorId);
        return ResponseEntity.ok(reservaService.buscarReservasPorDoctor(doctorId));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasPorEstado(@RequestParam String estado) {
        log.info("Solicitud para buscar reservas por estado: {}", estado);
        return ResponseEntity.ok(reservaService.buscarReservasPorEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {
        log.info("Solicitud para actualizar reserva con ID: {}", id);
        return ResponseEntity.ok(reservaService.actualizarReserva(id, requestDTO));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstadoReserva(
            @PathVariable Long id,
            @RequestParam String estado) {
        log.info("Solicitud para cambiar estado de reserva ID: {} a {}", id, estado);
        return ResponseEntity.ok(reservaService.cambiarEstadoReserva(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente reserva con ID: {}", id);
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
