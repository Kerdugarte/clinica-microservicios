package com.clinica.auth_service.controller;

import com.clinica.auth_service.dto.LoginRequestDTO;
import com.clinica.auth_service.dto.LoginResponseDTO;
import com.clinica.auth_service.dto.RegistroRequestDTO;
import com.clinica.auth_service.service.AuthService;
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
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    public ResponseEntity<LoginResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO requestDTO) {
        log.info("POST /api/v1/auth/registro - registrar credencial");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        log.info("POST /api/v1/auth/login - iniciar sesion");
        return ResponseEntity.ok(service.login(requestDTO));
    }

    @GetMapping("/validar")
    public ResponseEntity<Boolean> validarCredencial(@RequestParam String email) {
        log.info("GET /api/v1/auth/validar?email={} - validar credencial", email);
        return ResponseEntity.ok(service.validarCredencial(email));
    }
}
