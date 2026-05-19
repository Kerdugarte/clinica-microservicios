package com.clinica.ms_historial.controller;

import com.clinica.ms_historial.dto.HistorialRequestDTO;
import com.clinica.ms_historial.dto.HistorialResponseDTO;
import com.clinica.ms_historial.service.HistorialService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historiales")
public class HistorialController {

    private static final Logger log = LoggerFactory.getLogger(HistorialController.class);

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @PostMapping
    public ResponseEntity<HistorialResponseDTO> crearHistorial(@Valid @RequestBody HistorialRequestDTO requestDTO) {
        log.info("Solicitud para crear historial de paciente ID: {}, doctor ID: {}, reserva ID: {}",
                requestDTO.getPacienteId(), requestDTO.getDoctorId(), requestDTO.getReservaId());
        HistorialResponseDTO response = historialService.crearHistorial(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HistorialResponseDTO>> listarHistoriales() {
        log.info("Solicitud para listar historiales");
        return ResponseEntity.ok(historialService.listarHistoriales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> buscarHistorialPorId(@PathVariable Long id) {
        log.info("Solicitud para buscar historial por ID: {}", id);
        return ResponseEntity.ok(historialService.buscarHistorialPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorPaciente(@PathVariable Long pacienteId) {
        log.info("Solicitud para buscar historiales por paciente ID: {}", pacienteId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorPaciente(pacienteId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorDoctor(@PathVariable Long doctorId) {
        log.info("Solicitud para buscar historiales por doctor ID: {}", doctorId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorDoctor(doctorId));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorReserva(@PathVariable Long reservaId) {
        log.info("Solicitud para buscar historiales por reserva ID: {}", reservaId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorReserva(reservaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> actualizarHistorial(
            @PathVariable Long id,
            @Valid @RequestBody HistorialRequestDTO requestDTO) {
        log.info("Solicitud para actualizar historial con ID: {}", id);
        return ResponseEntity.ok(historialService.actualizarHistorial(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente historial con ID: {}", id);
        historialService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}
