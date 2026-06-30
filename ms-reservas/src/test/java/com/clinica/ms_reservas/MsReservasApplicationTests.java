package com.clinica.ms_reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.clinica.ms_reservas.client.DoctorClient;
import com.clinica.ms_reservas.client.PacienteClient;
import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;
import com.clinica.ms_reservas.model.Reserva;
import com.clinica.ms_reservas.repository.ReservaRepository;
import com.clinica.ms_reservas.service.ReservaServiceImpl;

class MsReservasApplicationTests {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private DoctorClient doctorClient;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearReserva_deberiaGuardarReservaCorrectamente() {
        // Given
        ReservaRequestDTO request = new ReservaRequestDTO();
        request.setPacienteId(1L);
        request.setDoctorId(1L);
        request.setFechaReserva(LocalDate.now().plusDays(1));
        request.setHoraReserva(LocalTime.of(10, 0));
        request.setMotivo("Control medico general");
        request.setEstado("PENDIENTE");

        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setId(1L);
        reservaGuardada.setPacienteId(1L);
        reservaGuardada.setDoctorId(1L);
        reservaGuardada.setFechaReserva(LocalDate.now().plusDays(1));
        reservaGuardada.setHoraReserva(LocalTime.of(10, 0));
        reservaGuardada.setMotivo("Control medico general");
        reservaGuardada.setEstado("PENDIENTE");
        reservaGuardada.setActivo(true);

        when(pacienteClient.buscarPacientePorId(1L)).thenReturn(new Object());
        when(doctorClient.buscarDoctorPorId(1L)).thenReturn(new Object());
        when(reservaRepository.existsByDoctorIdAndFechaReservaAndHoraReservaAndActivoTrue(
                request.getDoctorId(), request.getFechaReserva(), request.getHoraReserva())).thenReturn(false);
        when(reservaRepository.existsByPacienteIdAndFechaReservaAndHoraReservaAndActivoTrue(
                request.getPacienteId(), request.getFechaReserva(), request.getHoraReserva())).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        // When
        ReservaResponseDTO response = reservaService.crearReserva(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(1L, response.getDoctorId());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals("Control medico general", response.getMotivo());

        verify(pacienteClient, times(1)).buscarPacientePorId(1L);
        verify(doctorClient, times(1)).buscarDoctorPorId(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void buscarReservaPorId_deberiaRetornarReservaCuandoExiste() {
        // Given
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setPacienteId(1L);
        reserva.setDoctorId(1L);
        reserva.setFechaReserva(LocalDate.now().plusDays(1));
        reserva.setHoraReserva(LocalTime.of(10, 0));
        reserva.setMotivo("Control medico general");
        reserva.setEstado("PENDIENTE");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        // When
        ReservaResponseDTO response = reservaService.buscarReservaPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(1L, response.getDoctorId());
        assertEquals("PENDIENTE", response.getEstado());

        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarReserva_deberiaDesactivarReserva() {
        // Given
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setPacienteId(1L);
        reserva.setDoctorId(1L);
        reserva.setFechaReserva(LocalDate.now().plusDays(1));
        reserva.setHoraReserva(LocalTime.of(10, 0));
        reserva.setMotivo("Control medico general");
        reserva.setEstado("PENDIENTE");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // When
        reservaService.eliminarReserva(1L);

        // Then
        assertFalse(reserva.getActivo());
        verify(reservaRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(reserva);
    }
}
