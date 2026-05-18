package com.clinica.security_service.controller;

import com.clinica.security_service.dto.PermisoRequestDTO;
import com.clinica.security_service.dto.PermisoResponseDTO;
import com.clinica.security_service.dto.RolRequestDTO;
import com.clinica.security_service.dto.RolResponseDTO;
import com.clinica.security_service.service.SecurityService;
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
@RequestMapping("/api/v1/security")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService service;

    public SecurityController(SecurityService service) {
        this.service = service;
    }

    @PostMapping("/roles")
    public ResponseEntity<RolResponseDTO> crearRol(@Valid @RequestBody RolRequestDTO requestDTO) {
        log.info("POST /api/v1/security/roles - crear rol");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearRol(requestDTO));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RolResponseDTO>> listarRoles() {
        log.info("GET /api/v1/security/roles - listar roles");
        return ResponseEntity.ok(service.listarRoles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RolResponseDTO> buscarRolPorId(@PathVariable Long id) {
        log.info("GET /api/v1/security/roles/{} - buscar rol por ID", id);
        return ResponseEntity.ok(service.buscarRolPorId(id));
    }

    @GetMapping("/roles/buscar")
    public ResponseEntity<RolResponseDTO> buscarRolPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/security/roles/buscar?nombre={} - buscar rol por nombre", nombre);
        return ResponseEntity.ok(service.buscarRolPorNombre(nombre));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RolResponseDTO> actualizarRol(
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO requestDTO) {
        log.info("PUT /api/v1/security/roles/{} - actualizar rol", id);
        return ResponseEntity.ok(service.actualizarRol(id, requestDTO));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        log.info("DELETE /api/v1/security/roles/{} - eliminar rol", id);
        service.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles/validar")
    public ResponseEntity<Boolean> validarRol(@RequestParam String nombre) {
        log.info("GET /api/v1/security/roles/validar?nombre={} - validar rol", nombre);
        return ResponseEntity.ok(service.validarRol(nombre));
    }

    @PostMapping("/permisos")
    public ResponseEntity<PermisoResponseDTO> crearPermiso(@Valid @RequestBody PermisoRequestDTO requestDTO) {
        log.info("POST /api/v1/security/permisos - crear permiso");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPermiso(requestDTO));
    }

    @GetMapping("/permisos")
    public ResponseEntity<List<PermisoResponseDTO>> listarPermisos() {
        log.info("GET /api/v1/security/permisos - listar permisos");
        return ResponseEntity.ok(service.listarPermisos());
    }

    @GetMapping("/permisos/{id}")
    public ResponseEntity<PermisoResponseDTO> buscarPermisoPorId(@PathVariable Long id) {
        log.info("GET /api/v1/security/permisos/{} - buscar permiso por ID", id);
        return ResponseEntity.ok(service.buscarPermisoPorId(id));
    }

    @GetMapping("/permisos/buscar")
    public ResponseEntity<PermisoResponseDTO> buscarPermisoPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/security/permisos/buscar?nombre={} - buscar permiso por nombre", nombre);
        return ResponseEntity.ok(service.buscarPermisoPorNombre(nombre));
    }

    @PutMapping("/permisos/{id}")
    public ResponseEntity<PermisoResponseDTO> actualizarPermiso(
            @PathVariable Long id,
            @Valid @RequestBody PermisoRequestDTO requestDTO) {
        log.info("PUT /api/v1/security/permisos/{} - actualizar permiso", id);
        return ResponseEntity.ok(service.actualizarPermiso(id, requestDTO));
    }

    @DeleteMapping("/permisos/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Long id) {
        log.info("DELETE /api/v1/security/permisos/{} - eliminar permiso", id);
        service.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permisos/validar")
    public ResponseEntity<Boolean> validarPermiso(@RequestParam String nombre) {
        log.info("GET /api/v1/security/permisos/validar?nombre={} - validar permiso", nombre);
        return ResponseEntity.ok(service.validarPermiso(nombre));
    }
}
