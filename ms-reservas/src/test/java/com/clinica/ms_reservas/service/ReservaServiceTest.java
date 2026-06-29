package com.clinica.ms_reservas.service;

import com.clinica.ms_reservas.client.DoctorClient;
import com.clinica.ms_reservas.client.PacienteClient;
import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;
import com.clinica.ms_reservas.model.Reserva;
import com.clinica.ms_reservas.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private DoctorClient doctorClient;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Test
    void deberiaCrearReservaCorrectamente() {

        // Arrange: se preparan los datos de entrada y se simulan las dependencias externas
        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setPacienteId(1L);
        request.setDoctorId(1L);
        request.setFechaReserva(LocalDate.of(2026, 7, 1));
        request.setHoraReserva(LocalTime.of(10, 0));
        request.setMotivoConsulta("Control general");

        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setId(1L);
        reservaGuardada.setPacienteId(1L);
        reservaGuardada.setDoctorId(1L);
        reservaGuardada.setFechaReserva(LocalDate.of(2026, 7, 1));
        reservaGuardada.setHoraReserva(LocalTime.of(10, 0));
        reservaGuardada.setMotivoConsulta("Control general");
        reservaGuardada.setEstado("AGENDADA");
        reservaGuardada.setActivo(true);

        when(pacienteClient.existePaciente(1L)).thenReturn(true);
        when(doctorClient.existeDoctor(1L)).thenReturn(true);
        when(reservaRepository.existsByDoctorIdAndFechaReservaAndHoraReservaAndActivoTrue(
                1L,
                LocalDate.of(2026, 7, 1),
                LocalTime.of(10, 0)
        )).thenReturn(false);
        when(reservaRepository.existsByPacienteIdAndFechaReservaAndHoraReservaAndActivoTrue(
                1L,
                LocalDate.of(2026, 7, 1),
                LocalTime.of(10, 0)
        )).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        // Act: se ejecuta el método real del service
        ReservaResponseDTO response = reservaService.crearReserva(request);

        // Assert: se valida que la reserva fue creada correctamente
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(1L, response.getDoctorId());
        assertEquals("AGENDADA", response.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void deberiaBuscarReservaPorIdCuandoExiste() {

        // Arrange: se simula una reserva existente en el repositorio
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setPacienteId(1L);
        reserva.setDoctorId(1L);
        reserva.setFechaReserva(LocalDate.of(2026, 7, 1));
        reserva.setHoraReserva(LocalTime.of(10, 0));
        reserva.setMotivoConsulta("Control general");
        reserva.setEstado("AGENDADA");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        // Act
        ReservaResponseDTO response = reservaService.buscarReservaPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("AGENDADA", response.getEstado());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void deberiaEliminarReservaCambiandoActivoAFalse() {

        // Arrange: se simula una reserva activa
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setPacienteId(1L);
        reserva.setDoctorId(1L);
        reserva.setFechaReserva(LocalDate.of(2026, 7, 1));
        reserva.setHoraReserva(LocalTime.of(10, 0));
        reserva.setMotivoConsulta("Control general");
        reserva.setEstado("AGENDADA");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // Act
        reservaService.eliminarReserva(1L);

        // Assert
        assertFalse(reserva.getActivo());
        verify(reservaRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(reserva);
    }
}
