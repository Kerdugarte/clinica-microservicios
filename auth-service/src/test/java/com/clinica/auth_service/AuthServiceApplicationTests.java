package com.clinica.auth_service;

import com.clinica.auth_service.dto.LoginRequestDTO;
import com.clinica.auth_service.dto.LoginResponseDTO;
import com.clinica.auth_service.dto.RegistroRequestDTO;
import com.clinica.auth_service.exception.AuthException;
import com.clinica.auth_service.model.Credencial;
import com.clinica.auth_service.repository.CredencialRepository;
import com.clinica.auth_service.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceApplicationTests {

    @Mock
    private CredencialRepository credencialRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrar_deberiaGuardarCredencialCorrectamente() {
        // Given
        RegistroRequestDTO request = new RegistroRequestDTO();
        request.setEmail("usuario@clinica.cl");
        request.setPassword("123456");
        request.setRol("ADMIN");

        Credencial credencialGuardada = new Credencial();
        credencialGuardada.setId(1L);
        credencialGuardada.setEmail("usuario@clinica.cl");
        credencialGuardada.setPassword("123456");
        credencialGuardada.setRol("ADMIN");
        credencialGuardada.setActivo(true);

        when(credencialRepository.existsByEmail("usuario@clinica.cl")).thenReturn(false);
        when(credencialRepository.save(any(Credencial.class))).thenReturn(credencialGuardada);

        // When
        LoginResponseDTO response = authService.registrar(request);

        // Then
        assertNotNull(response);
        assertEquals("usuario@clinica.cl", response.getEmail());
        assertEquals("ADMIN", response.getRol());
        assertEquals("Registro exitoso", response.getMensaje());
        assertTrue(response.getToken().startsWith("TOKEN-"));

        verify(credencialRepository, times(1)).existsByEmail("usuario@clinica.cl");
        verify(credencialRepository, times(1)).save(any(Credencial.class));
    }

    @Test
    void login_deberiaRetornarTokenCuandoCredencialesSonCorrectas() {
        // Given
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("usuario@clinica.cl");
        request.setPassword("123456");

        Credencial credencial = new Credencial();
        credencial.setId(1L);
        credencial.setEmail("usuario@clinica.cl");
        credencial.setPassword("123456");
        credencial.setRol("ADMIN");
        credencial.setActivo(true);

        when(credencialRepository.findByEmail("usuario@clinica.cl")).thenReturn(Optional.of(credencial));

        // When
        LoginResponseDTO response = authService.login(request);

        // Then
        assertNotNull(response);
        assertEquals("usuario@clinica.cl", response.getEmail());
        assertEquals("ADMIN", response.getRol());
        assertEquals("Login exitoso", response.getMensaje());
        assertTrue(response.getToken().startsWith("TOKEN-"));

        verify(credencialRepository, times(1)).findByEmail("usuario@clinica.cl");
    }

    @Test
    void login_deberiaLanzarExcepcionCuandoPasswordEsIncorrecta() {
        // Given
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("usuario@clinica.cl");
        request.setPassword("incorrecta");

        Credencial credencial = new Credencial();
        credencial.setId(1L);
        credencial.setEmail("usuario@clinica.cl");
        credencial.setPassword("123456");
        credencial.setRol("ADMIN");
        credencial.setActivo(true);

        when(credencialRepository.findByEmail("usuario@clinica.cl")).thenReturn(Optional.of(credencial));

        // When / Then
        assertThrows(AuthException.class, () -> authService.login(request));

        verify(credencialRepository, times(1)).findByEmail("usuario@clinica.cl");
    }

    @Test
    void validarCredencial_deberiaRetornarTrueCuandoExisteActiva() {
        // Given
        when(credencialRepository.existsByEmailAndActivoTrue("usuario@clinica.cl")).thenReturn(true);

        // When
        boolean resultado = authService.validarCredencial("usuario@clinica.cl");

        // Then
        assertTrue(resultado);
        verify(credencialRepository, times(1)).existsByEmailAndActivoTrue("usuario@clinica.cl");
    }
}
