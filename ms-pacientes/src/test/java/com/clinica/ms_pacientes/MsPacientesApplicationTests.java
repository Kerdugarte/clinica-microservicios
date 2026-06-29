package com.clinica.ms_pacientes;

import com.clinica.ms_pacientes.dto.PacienteRequestDTO;
import com.clinica.ms_pacientes.dto.PacienteResponseDTO;
import com.clinica.ms_pacientes.model.Paciente;
import com.clinica.ms_pacientes.repository.PacienteRepository;
import com.clinica.ms_pacientes.service.PacienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MsPacientesApplicationTests {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPaciente_deberiaGuardarPacienteCorrectamente() {
        // Given
        PacienteRequestDTO request = new PacienteRequestDTO();
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setRut("12345678-9");
        request.setEmail("juan@correo.com");
        request.setTelefono("912345678");
        request.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        request.setDireccion("Av Siempre Viva 123");

        Paciente pacienteGuardado = new Paciente();
        pacienteGuardado.setId(1L);
        pacienteGuardado.setNombre("Juan");
        pacienteGuardado.setApellido("Perez");
        pacienteGuardado.setRut("12345678-9");
        pacienteGuardado.setEmail("juan@correo.com");
        pacienteGuardado.setTelefono("912345678");
        pacienteGuardado.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        pacienteGuardado.setDireccion("Av Siempre Viva 123");
        pacienteGuardado.setActivo(true);

        when(pacienteRepository.existsByRut("12345678-9")).thenReturn(false);
        when(pacienteRepository.existsByEmail("juan@correo.com")).thenReturn(false);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteGuardado);

        // When
        PacienteResponseDTO response = pacienteService.crearPaciente(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        assertEquals("Perez", response.getApellido());
        assertEquals("12345678-9", response.getRut());
        assertEquals("juan@correo.com", response.getEmail());
        assertEquals("Av Siempre Viva 123", response.getDireccion());

        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void buscarPacientePorId_deberiaRetornarPacienteCuandoExiste() {
        // Given
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setRut("12345678-9");
        paciente.setEmail("juan@correo.com");
        paciente.setTelefono("912345678");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setDireccion("Av Siempre Viva 123");
        paciente.setActivo(true);

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        // When
        PacienteResponseDTO response = pacienteService.buscarPacientePorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        assertEquals("12345678-9", response.getRut());

        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarPaciente_deberiaDesactivarPaciente() {
        // Given
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setRut("12345678-9");
        paciente.setEmail("juan@correo.com");
        paciente.setTelefono("912345678");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setDireccion("Av Siempre Viva 123");
        paciente.setActivo(true);

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        // When
        pacienteService.eliminarPaciente(1L);

        // Then
        assertFalse(paciente.getActivo());
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).save(paciente);
    }
}
 