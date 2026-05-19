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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("Solicitud para crear paciente con RUT: {}", requestDTO.getRut());
        PacienteResponseDTO response = pacienteService.crearPaciente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        log.info("Solicitud para listar pacientes");
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorId(@PathVariable Long id) {
        log.info("Solicitud para buscar paciente por ID: {}", id);
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<PacienteResponseDTO> buscarPacientePorRut(@RequestParam String rut) {
        log.info("Solicitud para buscar paciente por RUT: {}", rut);
        return ResponseEntity.ok(pacienteService.buscarPacientePorRut(rut));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("Solicitud para actualizar paciente con ID: {}", id);
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente paciente con ID: {}", id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar-rut")
    public ResponseEntity<Boolean> validarPacientePorRut(@RequestParam String rut) {
        log.info("Solicitud para validar paciente por RUT: {}", rut);
        return ResponseEntity.ok(pacienteService.validarPacientePorRut(rut));
    }

    @GetMapping("/validar-email")
    public ResponseEntity<Boolean> validarPacientePorEmail(@RequestParam String email) {
        log.info("Solicitud para validar paciente por email: {}", email);
        return ResponseEntity.ok(pacienteService.validarPacientePorEmail(email));
    }
}
