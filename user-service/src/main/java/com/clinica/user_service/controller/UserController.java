package com.clinica.user_service.controller;

import com.clinica.user_service.dto.UserRequestDTO;
import com.clinica.user_service.dto.UserResponseDTO;
import com.clinica.user_service.service.UserService;
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
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios del sistema clínico")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> crearUsuario(@Valid @RequestBody UserRequestDTO requestDTO) {
        log.info("POST /api/v1/usuarios - crear usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(requestDTO));
    }

    @Operation(summary = "Listar usuarios", description = "Obtiene el listado completo de usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listarUsuarios() {
        log.info("GET /api/v1/usuarios - listar usuarios");
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene la información de un usuario mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/v1/usuarios/{} - buscar usuario", id);
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Buscar usuario por email", description = "Obtiene la información de un usuario mediante su correo electrónico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<UserResponseDTO> buscarPorEmail(
            @Parameter(description = "Correo electrónico del usuario", example = "usuario@clinica.cl")
            @RequestParam String email) {
        log.info("GET /api/v1/usuarios/buscar?email={} - buscar usuario", email);
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> actualizarUsuario(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        log.info("PUT /api/v1/usuarios/{} - actualizar usuario", id);
        return ResponseEntity.ok(service.actualizarUsuario(id, requestDTO));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina lógicamente un usuario mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/usuarios/{} - eliminar usuario", id);
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar usuario", description = "Verifica si existe un usuario mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Validación realizada correctamente")
    @GetMapping("/validar/{id}")
    public ResponseEntity<Boolean> validarUsuario(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/v1/usuarios/validar/{} - validar existencia", id);
        return ResponseEntity.ok(service.existeUsuario(id));
    }
}
