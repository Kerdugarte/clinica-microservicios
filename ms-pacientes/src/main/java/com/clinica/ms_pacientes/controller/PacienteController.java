package com.clinica.ms_pacientes.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.ms_pacientes.dto.PacienteRequestDTO;
import com.clinica.ms_pacientes.dto.PacienteResponseDTO;
import com.clinica.ms_pacientes.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pacientes")
@Tag(name = "Pacientes", description = "Endpoints para la gestión de pacientes de la clínica")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Crear paciente", description = "Registra un nuevo paciente en el sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o paciente ya existente")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("Solicitud para crear paciente con RUT: {}", requestDTO.getRut());
        PacienteResponseDTO response = pacienteService.crearPaciente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar pacientes", description = "Obtiene el listado completo de pacientes registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        log.info("Solicitud para listar pacientes");
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @Operation(summary = "Buscar paciente por ID", description = "Obtiene la información de un paciente mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorId(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable("id") Long id) {
        log.info("Solicitud para buscar paciente por ID: {}", id);
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(id));
    }

    @Operation(summary = "Buscar paciente por RUT", description = "Obtiene la información de un paciente mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorRut(
            @Parameter(description = "RUT del paciente", example = "12345678-9")
            @RequestParam("rut") String rut) {
        log.info("Solicitud para buscar paciente por RUT: {}", rut);
        return ResponseEntity.ok(pacienteService.buscarPacientePorRut(rut));
    }

    @Operation(summary = "Actualizar paciente", description = "Actualiza los datos de un paciente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("Solicitud para actualizar paciente con ID: {}", id);
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, requestDTO));
    }

    @Operation(summary = "Eliminar paciente", description = "Elimina lógicamente un paciente mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(
            @Parameter(description = "Identificador del paciente", example = "1")
            @PathVariable("id") Long id) {
        log.info("Solicitud para eliminar logicamente paciente con ID: {}", id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar paciente por RUT", description = "Valida si existe un paciente registrado con el RUT indicado.")
    @ApiResponse(responseCode = "200", description = "Resultado de validación obtenido correctamente")
    @GetMapping("/validar-rut")
    public ResponseEntity<Boolean> validarPacientePorRut(
            @Parameter(description = "RUT del paciente", example = "12345678-9")
            @RequestParam("rut") String rut) {
        log.info("Solicitud para validar paciente por RUT: {}", rut);
        return ResponseEntity.ok(pacienteService.validarPacientePorRut(rut));
    }

    @Operation(summary = "Validar paciente por email", description = "Valida si existe un paciente registrado con el correo electrónico indicado.")
    @ApiResponse(responseCode = "200", description = "Resultado de validación obtenido correctamente")
    @GetMapping("/validar-email")
    public ResponseEntity<Boolean> validarPacientePorEmail(
            @Parameter(description = "Correo electrónico del paciente", example = "paciente@correo.com")
            @RequestParam("email") String email) {
        log.info("Solicitud para validar paciente por email: {}", email);
        return ResponseEntity.ok(pacienteService.validarPacientePorEmail(email));
    }
}
