package com.clinica.auth_service.controller;

import com.clinica.auth_service.dto.LoginRequestDTO;
import com.clinica.auth_service.dto.LoginResponseDTO;
import com.clinica.auth_service.dto.RegistroRequestDTO;
import com.clinica.auth_service.service.AuthService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro, inicio de sesión y validación de credenciales")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar credencial", description = "Registra una nueva credencial de acceso para un usuario del sistema clínico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credencial registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/registro")
    public ResponseEntity<LoginResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO requestDTO) {
        log.info("POST /api/v1/auth/registro - registrar credencial");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(requestDTO));
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica una credencial y retorna la respuesta de acceso correspondiente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión correcto"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        log.info("POST /api/v1/auth/login - iniciar sesion");
        return ResponseEntity.ok(service.login(requestDTO));
    }

    @Operation(summary = "Validar credencial", description = "Verifica si existe una credencial registrada mediante el correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Validación realizada correctamente")
    @GetMapping("/validar")
    public ResponseEntity<Boolean> validarCredencial(
            @Parameter(description = "Correo electrónico de la credencial", example = "usuario@clinica.cl")
            @RequestParam String email) {
        log.info("GET /api/v1/auth/validar?email={} - validar credencial", email);
        return ResponseEntity.ok(service.validarCredencial(email));
    }
}
