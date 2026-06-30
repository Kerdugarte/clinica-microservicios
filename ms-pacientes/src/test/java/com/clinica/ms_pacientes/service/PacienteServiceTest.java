package com.clinica.ms_pacientes.service;

import com.clinica.ms_pacientes.dto.PacienteResponseDTO;
import com.clinica.ms_pacientes.model.Paciente;
import com.clinica.ms_pacientes.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    @Test
    void deberiaListarPacientes() {

        // Arrange: se crea un paciente simulado
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setRut("12345678-9");

        // Se simula el repository
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        // Act: se ejecuta el método del service
        List<PacienteResponseDTO> resultado = pacienteService.listarPacientes();

        // Assert: se valida el resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void deberiaRetornarListaVacia() {

        // Arrange: repository devuelve lista vacía
        when(pacienteRepository.findAll()).thenReturn(List.of());

        // Act
        List<PacienteResponseDTO> resultado = pacienteService.listarPacientes();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
