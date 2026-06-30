package com.clinica.user_service;

import com.clinica.user_service.dto.UserRequestDTO;
import com.clinica.user_service.dto.UserResponseDTO;
import com.clinica.user_service.model.User;
import com.clinica.user_service.repository.UserRepository;
import com.clinica.user_service.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_deberiaGuardarUsuarioCorrectamente() {
        // Given
        UserRequestDTO request = new UserRequestDTO();
        request.setNombre("Carlos");
        request.setApellido("Perez");
        request.setEmail("carlos@clinica.cl");
        request.setRol("ADMIN");

        User usuarioGuardado = new User();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Carlos");
        usuarioGuardado.setApellido("Perez");
        usuarioGuardado.setEmail("carlos@clinica.cl");
        usuarioGuardado.setRol("ADMIN");
        usuarioGuardado.setActivo(true);

        when(userRepository.existsByEmail("carlos@clinica.cl")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(usuarioGuardado);

        // When
        UserResponseDTO response = userService.crearUsuario(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Carlos", response.getNombre());
        assertEquals("Perez", response.getApellido());
        assertEquals("carlos@clinica.cl", response.getEmail());
        assertEquals("ADMIN", response.getRol());

        verify(userRepository, times(1)).existsByEmail("carlos@clinica.cl");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void buscarPorId_deberiaRetornarUsuarioCuandoExiste() {
        // Given
        User usuario = new User();
        usuario.setId(1L);
        usuario.setNombre("Carlos");
        usuario.setApellido("Perez");
        usuario.setEmail("carlos@clinica.cl");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When
        UserResponseDTO response = userService.buscarPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Carlos", response.getNombre());
        assertEquals("carlos@clinica.cl", response.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarUsuario_deberiaDesactivarUsuario() {
        // Given
        User usuario = new User();
        usuario.setId(1L);
        usuario.setNombre("Carlos");
        usuario.setApellido("Perez");
        usuario.setEmail("carlos@clinica.cl");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(userRepository.save(any(User.class))).thenReturn(usuario);

        // When
        userService.eliminarUsuario(1L);

        // Then
        assertFalse(usuario.getActivo());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(usuario);
    }
}
