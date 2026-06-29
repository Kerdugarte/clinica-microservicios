package com.clinica.ms_pagos;

import com.clinica.ms_pagos.client.ReservaClient;
import com.clinica.ms_pagos.dto.PagoRequestDTO;
import com.clinica.ms_pagos.dto.PagoResponseDTO;
import com.clinica.ms_pagos.model.Pago;
import com.clinica.ms_pagos.repository.PagoRepository;
import com.clinica.ms_pagos.service.PagoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MsPagosApplicationTests {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private PagoServiceImpl pagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPago_deberiaGuardarPagoCorrectamente() {
        // Given
        PagoRequestDTO request = new PagoRequestDTO();
        request.setReservaId(1L);
        request.setMonto(new BigDecimal("25000"));
        request.setMetodoPago("TRANSFERENCIA");
        request.setEstadoPago("PENDIENTE");

        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(1L);
        pagoGuardado.setReservaId(1L);
        pagoGuardado.setMonto(new BigDecimal("25000"));
        pagoGuardado.setMetodoPago("TRANSFERENCIA");
        pagoGuardado.setEstadoPago("PENDIENTE");
        pagoGuardado.setFechaPago(LocalDateTime.now());
        pagoGuardado.setActivo(true);

        when(reservaClient.buscarReservaPorId(1L)).thenReturn(new Object());
        when(pagoRepository.existsByReservaIdAndActivoTrue(1L)).thenReturn(false);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

        // When
        PagoResponseDTO response = pagoService.crearPago(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getReservaId());
        assertEquals(new BigDecimal("25000"), response.getMonto());
        assertEquals("TRANSFERENCIA", response.getMetodoPago());
        assertEquals("PENDIENTE", response.getEstadoPago());

        verify(reservaClient, times(1)).buscarReservaPorId(1L);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void buscarPagoPorId_deberiaRetornarPagoCuandoExiste() {
        // Given
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(1L);
        pago.setMonto(new BigDecimal("25000"));
        pago.setMetodoPago("TRANSFERENCIA");
        pago.setEstadoPago("PENDIENTE");
        pago.setFechaPago(LocalDateTime.now());
        pago.setActivo(true);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        // When
        PagoResponseDTO response = pagoService.buscarPagoPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getReservaId());
        assertEquals("PENDIENTE", response.getEstadoPago());

        verify(pagoRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarPago_deberiaDesactivarPago() {
        // Given
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(1L);
        pago.setMonto(new BigDecimal("25000"));
        pago.setMetodoPago("TRANSFERENCIA");
        pago.setEstadoPago("PENDIENTE");
        pago.setFechaPago(LocalDateTime.now());
        pago.setActivo(true);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        // When
        pagoService.eliminarPago(1L);

        // Then
        assertFalse(pago.getActivo());
        verify(pagoRepository, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(pago);
    }
}
