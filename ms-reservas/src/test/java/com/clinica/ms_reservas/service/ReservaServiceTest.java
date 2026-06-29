package com.clinica.ms_reservas.service;

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

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Test
    void deberiaBuscarReservaPorIdCuandoExiste() {

        // Arrange: se simula una reserva existente en el repositorio
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setPacienteId(1L);
        reserva.setDoctorId(1L);
        reserva.setFechaReserva(LocalDate.of(2026, 7, 1));
        reserva.setHoraReserva(LocalTime.of(10, 0));
        reserva.setEstado("AGENDADA");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        // Act: se ejecuta el método real del service
        ReservaResponseDTO response = reservaService.buscarReservaPorId(1L);

        // Assert: se valida el resultado esperado
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
        reserva.setEstado("AGENDADA");
        reserva.setActivo(true);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // Act: se ejecuta la eliminación lógica
        reservaService.eliminarReserva(1L);

        // Assert: se valida que la reserva quede inactiva
        assertFalse(reserva.getActivo());
        verify(reservaRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(reserva);
    }
}
