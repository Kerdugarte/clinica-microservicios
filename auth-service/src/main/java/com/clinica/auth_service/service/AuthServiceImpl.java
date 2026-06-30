package com.clinica.auth_service.service;

import com.clinica.auth_service.dto.LoginRequestDTO;
import com.clinica.auth_service.dto.LoginResponseDTO;
import com.clinica.auth_service.dto.RegistroRequestDTO;
import com.clinica.auth_service.exception.AuthException;
import com.clinica.auth_service.model.Credencial;
import com.clinica.auth_service.repository.CredencialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final CredencialRepository repository;

    public AuthServiceImpl(CredencialRepository repository) {
        this.repository = repository;
    }

    @Override
    public LoginResponseDTO registrar(RegistroRequestDTO requestDTO) {
        log.info("Registrando credencial para email: {}", requestDTO.getEmail());

        if (repository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe una credencial registrada con el email: " + requestDTO.getEmail());
        }

        Credencial credencial = new Credencial();
        credencial.setEmail(requestDTO.getEmail());
        credencial.setPassword(requestDTO.getPassword());
        credencial.setRol(requestDTO.getRol());
        credencial.setActivo(true);

        Credencial guardada = repository.save(credencial);

        log.info("Credencial registrada correctamente con ID: {}", guardada.getId());

        return new LoginResponseDTO(
                guardada.getEmail(),
                guardada.getRol(),
                generarToken(),
                "Registro exitoso"
        );
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        log.info("Intentando login para email: {}", requestDTO.getEmail());

        Credencial credencial = repository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new AuthException("Credenciales invalidas"));

        if (!Boolean.TRUE.equals(credencial.getActivo())) {
            throw new AuthException("La credencial se encuentra desactivada");
        }

        if (!credencial.getPassword().equals(requestDTO.getPassword())) {
            log.warn("Intento de login fallido para email: {}", requestDTO.getEmail());
            throw new AuthException("Credenciales invalidas");
        }

        log.info("Login exitoso para email: {}", requestDTO.getEmail());

        return new LoginResponseDTO(
                credencial.getEmail(),
                credencial.getRol(),
                generarToken(),
                "Login exitoso"
        );
    }

    @Override
    public boolean validarCredencial(String email) {
        log.info("Validando existencia de credencial activa para email: {}", email);
        return repository.existsByEmailAndActivoTrue(email);
    }

    private String generarToken() {
        return "TOKEN-" + UUID.randomUUID();
    }
}
