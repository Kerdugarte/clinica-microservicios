package com.clinica.ms_notificaciones;

import com.clinica.ms_notificaciones.client.ReservaClient;
import com.clinica.ms_notificaciones.dto.NotificacionRequestDTO;
import com.clinica.ms_notificaciones.dto.NotificacionResponseDTO;
import com.clinica.ms_notificaciones.model.Notificacion;
import com.clinica.ms_notificaciones.repository.NotificacionRepository;
import com.clinica.ms_notificaciones.service.NotificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MsNotificacionesApplicationTests {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearNotificacion_deberiaGuardarNotificacionCorrectamente() {
        // Given
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setReservaId(1L);
        request.setTipoNotificacion("EMAIL");
        request.setDestinatario("paciente@correo.com");
        request.setMensaje("Su reserva ha sido registrada correctamente");
        request.setEstado("PENDIENTE");

        Notificacion notificacionGuardada = new Notificacion();
        notificacionGuardada.setId(1L);
        notificacionGuardada.setReservaId(1L);
        notificacionGuardada.setTipoNotificacion("EMAIL");
        notificacionGuardada.setDestinatario("paciente@correo.com");
        notificacionGuardada.setMensaje("Su reserva ha sido registrada correctamente");
        notificacionGuardada.setEstado("PENDIENTE");
        notificacionGuardada.setFechaEnvio(LocalDateTime.now());
        notificacionGuardada.setActivo(true);

        when(reservaClient.buscarReservaPorId(1L)).thenReturn(new Object());
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);

        // When
        NotificacionResponseDTO response = notificacionService.crearNotificacion(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getReservaId());
        assertEquals("EMAIL", response.getTipoNotificacion());
        assertEquals("paciente@correo.com", response.getDestinatario());
        assertEquals("PENDIENTE", response.getEstado());

        verify(reservaClient, times(1)).buscarReservaPorId(1L);
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    void buscarNotificacionPorId_deberiaRetornarNotificacionCuandoExiste() {
        // Given
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setReservaId(1L);
        notificacion.setTipoNotificacion("EMAIL");
        notificacion.setDestinatario("paciente@correo.com");
        notificacion.setMensaje("Su reserva ha sido registrada correctamente");
        notificacion.setEstado("PENDIENTE");
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setActivo(true);

        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        // When
        NotificacionResponseDTO response = notificacionService.buscarNotificacionPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getReservaId());
        assertEquals("EMAIL", response.getTipoNotificacion());
        assertEquals("PENDIENTE", response.getEstado());

        verify(notificacionRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarNotificacion_deberiaDesactivarNotificacion() {
        // Given
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setReservaId(1L);
        notificacion.setTipoNotificacion("EMAIL");
        notificacion.setDestinatario("paciente@correo.com");
        notificacion.setMensaje("Su reserva ha sido registrada correctamente");
        notificacion.setEstado("PENDIENTE");
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setActivo(true);

        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        // When
        notificacionService.eliminarNotificacion(1L);

        // Then
        assertFalse(notificacion.getActivo());
        verify(notificacionRepository, times(1)).findById(1L);
        verify(notificacionRepository, times(1)).save(notificacion);
    }
}
