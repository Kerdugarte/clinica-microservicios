
package com.clinica.ms_doctores.controller;

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

import com.clinica.ms_doctores.dto.DoctorRequestDTO;
import com.clinica.ms_doctores.dto.DoctorResponseDTO;
import com.clinica.ms_doctores.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/doctores")
@Tag(name = "Doctores", description = "Endpoints para la gestión de doctores de la clínica")
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Crear doctor", description = "Registra un nuevo doctor en el sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o doctor ya existente")
    })
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> crearDoctor(@Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Solicitud para crear doctor con RUT: {}", requestDTO.getRut());
        DoctorResponseDTO response = doctorService.crearDoctor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar doctores", description = "Obtiene el listado completo de doctores registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> listarDoctores() {
        log.info("Solicitud para listar doctores");
        return ResponseEntity.ok(doctorService.listarDoctores());
    }

    @Operation(summary = "Buscar doctor por ID", description = "Obtiene la información de un doctor mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorId(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para buscar doctor por ID: {}", id);
        return ResponseEntity.ok(doctorService.buscarDoctorPorId(id));
    }

    @Operation(summary = "Buscar doctor por RUT", description = "Obtiene la información de un doctor mediante su RUT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/buscar-rut")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorRut(
            @Parameter(description = "RUT del doctor", example = "12345678-9")
            @RequestParam String rut) {
        log.info("Solicitud para buscar doctor por RUT: {}", rut);
        return ResponseEntity.ok(doctorService.buscarDoctorPorRut(rut));
    }

    @Operation(summary = "Buscar doctor por número de registro", description = "Obtiene la información de un doctor mediante su número de registro profesional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor encontrado"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @GetMapping("/buscar-registro")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorNumeroRegistro(
            @Parameter(description = "Número de registro profesional del doctor", example = "MED-12345")
            @RequestParam String numeroRegistro) {
        log.info("Solicitud para buscar doctor por numero de registro: {}", numeroRegistro);
        return ResponseEntity.ok(doctorService.buscarDoctorPorNumeroRegistro(numeroRegistro));
    }

    @Operation(summary = "Actualizar doctor", description = "Actualiza los datos de un doctor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> actualizarDoctor(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Solicitud para actualizar doctor con ID: {}", id);
        return ResponseEntity.ok(doctorService.actualizarDoctor(id, requestDTO));
    }

    @Operation(summary = "Eliminar doctor", description = "Elimina lógicamente un doctor mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDoctor(
            @Parameter(description = "Identificador del doctor", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente doctor con ID: {}", id);
        doctorService.eliminarDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar doctor por RUT", description = "Valida si existe un doctor registrado con el RUT indicado.")
    @ApiResponse(responseCode = "200", description = "Resultado de validación obtenido correctamente")
    @GetMapping("/validar-rut")
    public ResponseEntity<Boolean> validarDoctorPorRut(
            @Parameter(description = "RUT del doctor", example = "12345678-9")
            @RequestParam String rut) {
        log.info("Solicitud para validar doctor por RUT: {}", rut);
        return ResponseEntity.ok(doctorService.validarDoctorPorRut(rut));
    }

    @Operation(summary = "Validar doctor por email", description = "Valida si existe un doctor registrado con el correo electrónico indicado.")
    @ApiResponse(responseCode = "200", description = "Resultado de validación obtenido correctamente")
    @GetMapping("/validar-email")
    public ResponseEntity<Boolean> validarDoctorPorEmail(
            @Parameter(description = "Correo electrónico del doctor", example = "doctor@correo.com")
            @RequestParam String email) {
        log.info("Solicitud para validar doctor por email: {}", email);
        return ResponseEntity.ok(doctorService.validarDoctorPorEmail(email));
    }

    @Operation(summary = "Validar doctor por número de registro", description = "Valida si existe un doctor registrado con el número de registro profesional indicado.")
    @ApiResponse(responseCode = "200", description = "Resultado de validación obtenido correctamente")
    @GetMapping("/validar-registro")
    public ResponseEntity<Boolean> validarDoctorPorNumeroRegistro(
            @Parameter(description = "Número de registro profesional del doctor", example = "MED-12345")
            @RequestParam String numeroRegistro) {
        log.info("Solicitud para validar doctor por numero de registro: {}", numeroRegistro);
        return ResponseEntity.ok(doctorService.validarDoctorPorNumeroRegistro(numeroRegistro));
    }
}
