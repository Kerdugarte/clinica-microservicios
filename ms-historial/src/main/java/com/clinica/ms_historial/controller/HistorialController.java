package com.clinica.ms_historial.controller;

import com.clinica.ms_historial.dto.HistorialRequestDTO;
import com.clinica.ms_historial.dto.HistorialResponseDTO;
import com.clinica.ms_historial.service.HistorialService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historiales")
@Tag(name = "Historial Clínico", description = "Endpoints para la gestión del historial clínico de pacientes")
public class HistorialController {

    private static final Logger log = LoggerFactory.getLogger(HistorialController.class);

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @Operation(summary = "Crear historial clínico", description = "Registra un nuevo historial clínico asociado a paciente, doctor y reserva médica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Historial clínico creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente, doctor o reserva no encontrada")
    })
    @PostMapping
    public ResponseEntity<HistorialResponseDTO> crearHistorial(@Valid @RequestBody HistorialRequestDTO requestDTO) {
        log.info("Solicitud para crear historial de paciente ID: {}, doctor ID: {}, reserva ID: {}",
                requestDTO.getPacienteId(), requestDTO.getDoctorId(), requestDTO.getReservaId());
        HistorialResponseDTO response = historialService.crearHistorial(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar historiales clínicos", description = "Obtiene el listado completo de historiales clínicos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<HistorialResponseDTO>> listarHistoriales() {
        log.info("Solicitud para listar historiales");
        return ResponseEntity.ok(historialService.listarHistoriales());
    }

    @Operation(summary = "Buscar historial por ID", description = "Obtiene la información de un historial clínico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial clínico encontrado"),
            @ApiResponse(responseCode = "404", description = "Historial clínico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> buscarHistorialPorId(
            @Parameter(description = "Identificador del historial clínico", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para buscar historial por ID: {}", id);
        return ResponseEntity.ok(historialService.buscarHistorialPorId(id));
    }

    @Operation(summary = "Buscar historiales por paciente", description = "Obtiene todos los historiales clínicos asociados a un paciente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historiales del paciente obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorPaciente(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable Long pacienteId) {
        log.info("Solicitud para buscar historiales por paciente ID: {}", pacienteId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorPaciente(pacienteId));
    }

    @Operation(summary = "Buscar historiales por doctor", description = "Obtiene todos los historiales clínicos asociados a un doctor específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historiales del doctor obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorDoctor(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long doctorId) {
        log.info("Solicitud para buscar historiales por doctor ID: {}", doctorId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorDoctor(doctorId));
    }

    @Operation(summary = "Buscar historiales por reserva", description = "Obtiene todos los historiales clínicos asociados a una reserva médica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historiales de la reserva obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<HistorialResponseDTO>> buscarHistorialesPorReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long reservaId) {
        log.info("Solicitud para buscar historiales por reserva ID: {}", reservaId);
        return ResponseEntity.ok(historialService.buscarHistorialesPorReserva(reservaId));
    }

    @Operation(summary = "Actualizar historial clínico", description = "Actualiza los datos de un historial clínico existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial clínico actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Historial clínico no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HistorialResponseDTO> actualizarHistorial(
            @Parameter(description = "Identificador del historial clínico", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody HistorialRequestDTO requestDTO) {
        log.info("Solicitud para actualizar historial con ID: {}", id);
        return ResponseEntity.ok(historialService.actualizarHistorial(id, requestDTO));
    }

    @Operation(summary = "Eliminar historial clínico", description = "Elimina lógicamente un historial clínico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Historial clínico eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Historial clínico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(
            @Parameter(description = "Identificador del historial clínico", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente historial con ID: {}", id);
        historialService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}
