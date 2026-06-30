package com.clinica.security_service.controller;

import com.clinica.security_service.dto.PermisoRequestDTO;
import com.clinica.security_service.dto.PermisoResponseDTO;
import com.clinica.security_service.dto.RolRequestDTO;
import com.clinica.security_service.dto.RolResponseDTO;
import com.clinica.security_service.service.SecurityService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/security")
@Tag(name = "Seguridad", description = "Endpoints para la gestión de roles y permisos del sistema clínico")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService service;

    public SecurityController(SecurityService service) {
        this.service = service;
    }

    @Operation(summary = "Crear rol", description = "Registra un nuevo rol para el sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/roles")
    public ResponseEntity<RolResponseDTO> crearRol(@Valid @RequestBody RolRequestDTO requestDTO) {
        log.info("POST /api/v1/security/roles - crear rol");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearRol(requestDTO));
    }

    @Operation(summary = "Listar roles", description = "Obtiene el listado completo de roles registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de roles obtenido correctamente")
    @GetMapping("/roles")
    public ResponseEntity<List<RolResponseDTO>> listarRoles() {
        log.info("GET /api/v1/security/roles - listar roles");
        return ResponseEntity.ok(service.listarRoles());
    }

    @Operation(summary = "Buscar rol por ID", description = "Obtiene la información de un rol mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/roles/{id}")
    public ResponseEntity<RolResponseDTO> buscarRolPorId(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/v1/security/roles/{} - buscar rol por ID", id);
        return ResponseEntity.ok(service.buscarRolPorId(id));
    }

    @Operation(summary = "Buscar rol por nombre", description = "Obtiene la información de un rol mediante su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/roles/buscar")
    public ResponseEntity<RolResponseDTO> buscarRolPorNombre(
            @Parameter(description = "Nombre del rol", example = "ADMIN")
            @RequestParam String nombre) {
        log.info("GET /api/v1/security/roles/buscar?nombre={} - buscar rol por nombre", nombre);
        return ResponseEntity.ok(service.buscarRolPorNombre(nombre));
    }

    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/roles/{id}")
    public ResponseEntity<RolResponseDTO> actualizarRol(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO requestDTO) {
        log.info("PUT /api/v1/security/roles/{} - actualizar rol", id);
        return ResponseEntity.ok(service.actualizarRol(id, requestDTO));
    }

    @Operation(summary = "Eliminar rol", description = "Elimina lógicamente un rol mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> eliminarRol(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/security/roles/{} - eliminar rol", id);
        service.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar rol", description = "Verifica si un rol existe o es válido mediante su nombre.")
    @ApiResponse(responseCode = "200", description = "Validación realizada correctamente")
    @GetMapping("/roles/validar")
    public ResponseEntity<Boolean> validarRol(
            @Parameter(description = "Nombre del rol", example = "ADMIN")
            @RequestParam String nombre) {
        log.info("GET /api/v1/security/roles/validar?nombre={} - validar rol", nombre);
        return ResponseEntity.ok(service.validarRol(nombre));
    }

    @Operation(summary = "Crear permiso", description = "Registra un nuevo permiso para el sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/permisos")
    public ResponseEntity<PermisoResponseDTO> crearPermiso(@Valid @RequestBody PermisoRequestDTO requestDTO) {
        log.info("POST /api/v1/security/permisos - crear permiso");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPermiso(requestDTO));
    }

    @Operation(summary = "Listar permisos", description = "Obtiene el listado completo de permisos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de permisos obtenido correctamente")
    @GetMapping("/permisos")
    public ResponseEntity<List<PermisoResponseDTO>> listarPermisos() {
        log.info("GET /api/v1/security/permisos - listar permisos");
        return ResponseEntity.ok(service.listarPermisos());
    }

    @Operation(summary = "Buscar permiso por ID", description = "Obtiene la información de un permiso mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @GetMapping("/permisos/{id}")
    public ResponseEntity<PermisoResponseDTO> buscarPermisoPorId(
            @Parameter(description = "Identificador del permiso", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/v1/security/permisos/{} - buscar permiso por ID", id);
        return ResponseEntity.ok(service.buscarPermisoPorId(id));
    }

    @Operation(summary = "Buscar permiso por nombre", description = "Obtiene la información de un permiso mediante su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @GetMapping("/permisos/buscar")
    public ResponseEntity<PermisoResponseDTO> buscarPermisoPorNombre(
            @Parameter(description = "Nombre del permiso", example = "CREAR_RESERVA")
            @RequestParam String nombre) {
        log.info("GET /api/v1/security/permisos/buscar?nombre={} - buscar permiso por nombre", nombre);
        return ResponseEntity.ok(service.buscarPermisoPorNombre(nombre));
    }

    @Operation(summary = "Actualizar permiso", description = "Actualiza los datos de un permiso existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @PutMapping("/permisos/{id}")
    public ResponseEntity<PermisoResponseDTO> actualizarPermiso(
            @Parameter(description = "Identificador del permiso", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PermisoRequestDTO requestDTO) {
        log.info("PUT /api/v1/security/permisos/{} - actualizar permiso", id);
        return ResponseEntity.ok(service.actualizarPermiso(id, requestDTO));
    }

    @Operation(summary = "Eliminar permiso", description = "Elimina lógicamente un permiso mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permiso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @DeleteMapping("/permisos/{id}")
    public ResponseEntity<Void> eliminarPermiso(
            @Parameter(description = "Identificador del permiso", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/security/permisos/{} - eliminar permiso", id);
        service.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar permiso", description = "Verifica si un permiso existe o es válido mediante su nombre.")
    @ApiResponse(responseCode = "200", description = "Validación realizada correctamente")
    @GetMapping("/permisos/validar")
    public ResponseEntity<Boolean> validarPermiso(
            @Parameter(description = "Nombre del permiso", example = "CREAR_RESERVA")
            @RequestParam String nombre) {
        log.info("GET /api/v1/security/permisos/validar?nombre={} - validar permiso", nombre);
        return ResponseEntity.ok(service.validarPermiso(nombre));
    }
}
