package com.clinica.user_service.controller;

import com.clinica.user_service.dto.UserRequestDTO;
import com.clinica.user_service.dto.UserResponseDTO;
import com.clinica.user_service.service.UserService;
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
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> crearUsuario(@Valid @RequestBody UserRequestDTO requestDTO) {
        log.info("POST /api/v1/usuarios - crear usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listarUsuarios() {
        log.info("GET /api/v1/usuarios - listar usuarios");
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/v1/usuarios/{} - buscar usuario", id);
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<UserResponseDTO> buscarPorEmail(@RequestParam String email) {
        log.info("GET /api/v1/usuarios/buscar?email={} - buscar usuario", email);
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        log.info("PUT /api/v1/usuarios/{} - actualizar usuario", id);
        return ResponseEntity.ok(service.actualizarUsuario(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        log.info("DELETE /api/v1/usuarios/{} - eliminar usuario", id);
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar/{id}")
    public ResponseEntity<Boolean> validarUsuario(@PathVariable Long id) {
        log.info("GET /api/v1/usuarios/validar/{} - validar existencia", id);
        return ResponseEntity.ok(service.existeUsuario(id));
    }
}
