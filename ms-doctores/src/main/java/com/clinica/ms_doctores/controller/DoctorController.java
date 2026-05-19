
package com.clinica.ms_doctores.controller;

import com.clinica.ms_doctores.dto.DoctorRequestDTO;
import com.clinica.ms_doctores.dto.DoctorResponseDTO;
import com.clinica.ms_doctores.service.DoctorService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctores")
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> crearDoctor(@Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Solicitud para crear doctor con RUT: {}", requestDTO.getRut());
        DoctorResponseDTO response = doctorService.crearDoctor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> listarDoctores() {
        log.info("Solicitud para listar doctores");
        return ResponseEntity.ok(doctorService.listarDoctores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorId(@PathVariable Long id) {
        log.info("Solicitud para buscar doctor por ID: {}", id);
        return ResponseEntity.ok(doctorService.buscarDoctorPorId(id));
    }

    @GetMapping("/buscar-rut")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorRut(@RequestParam String rut) {
        log.info("Solicitud para buscar doctor por RUT: {}", rut);
        return ResponseEntity.ok(doctorService.buscarDoctorPorRut(rut));
    }

    @GetMapping("/buscar-registro")
    public ResponseEntity<DoctorResponseDTO> buscarDoctorPorNumeroRegistro(@RequestParam String numeroRegistro) {
        log.info("Solicitud para buscar doctor por numero de registro: {}", numeroRegistro);
        return ResponseEntity.ok(doctorService.buscarDoctorPorNumeroRegistro(numeroRegistro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> actualizarDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {
        log.info("Solicitud para actualizar doctor con ID: {}", id);
        return ResponseEntity.ok(doctorService.actualizarDoctor(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDoctor(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente doctor con ID: {}", id);
        doctorService.eliminarDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar-rut")
    public ResponseEntity<Boolean> validarDoctorPorRut(@RequestParam String rut) {
        log.info("Solicitud para validar doctor por RUT: {}", rut);
        return ResponseEntity.ok(doctorService.validarDoctorPorRut(rut));
    }

    @GetMapping("/validar-email")
    public ResponseEntity<Boolean> validarDoctorPorEmail(@RequestParam String email) {
        log.info("Solicitud para validar doctor por email: {}", email);
        return ResponseEntity.ok(doctorService.validarDoctorPorEmail(email));
    }

    @GetMapping("/validar-registro")
    public ResponseEntity<Boolean> validarDoctorPorNumeroRegistro(@RequestParam String numeroRegistro) {
        log.info("Solicitud para validar doctor por numero de registro: {}", numeroRegistro);
        return ResponseEntity.ok(doctorService.validarDoctorPorNumeroRegistro(numeroRegistro));
    }
}
