package com.clinica.ms_pacientes.controller;

import com.clinica.ms_pacientes.dto.PacienteResponseDTO;
import com.clinica.ms_pacientes.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PacienteController.class)
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Test
    void deberiaListarPacientes() throws Exception {

        // Arrange: se simula el servicio devolviendo una lista de pacientes
        when(pacienteService.listarPacientes())
                .thenReturn(List.of(new PacienteResponseDTO(
                        1L,
                        "Juan",
                        "Perez",
                        "12345678-9",
                        "juan@email.com",
                        "123456789",
                        null,
                        "Direccion",
                        true
                )));

        // Act + Assert: se ejecuta el endpoint y se valida respuesta HTTP 200
        mockMvc.perform(get("/api/v1/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void deberiaRetornarListaVacia() throws Exception {

        // Arrange: se simula el servicio devolviendo una lista vacía
        when(pacienteService.listarPacientes()).thenReturn(List.of());

        // Act + Assert: se ejecuta el endpoint y se valida respuesta HTTP 200
        mockMvc.perform(get("/api/v1/pacientes"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCrearPaciente() throws Exception {

        // Arrange: se prepara el JSON de entrada y la respuesta simulada del servicio
        String requestJson = """
                {
                    "nombre": "Juan",
                    "apellido": "Perez",
                    "rut": "12345678-9",
                    "email": "juan@email.com",
                    "telefono": "123456789",
                    "fechaNacimiento": "1990-01-01",
                    "direccion": "Direccion"
                }
                """;

        PacienteResponseDTO response = new PacienteResponseDTO(
                1L,
                "Juan",
                "Perez",
                "12345678-9",
                "juan@email.com",
                "123456789",
                null,
                "Direccion",
                true
        );

        when(pacienteService.crearPaciente(any())).thenReturn(response);

        // Act + Assert: se ejecuta el endpoint POST y se valida respuesta HTTP 201
        mockMvc.perform(post("/api/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.rut").value("12345678-9"));
    }
}
