package com.clinica.ms_pagos.service;

import com.clinica.ms_pagos.dto.PagoResponseDTO;
import com.clinica.ms_pagos.model.Pago;
import com.clinica.ms_pagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    @Test
    void deberiaBuscarPagoPorIdCuandoExiste() {

        // Arrange: se simula un pago existente en el repositorio
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(1L);
        pago.setMonto(new BigDecimal("25000"));
        pago.setMetodoPago("TARJETA");
        pago.setEstadoPago("PAGADO");
        pago.setFechaPago(LocalDateTime.now());
        pago.setActivo(true);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        // Act
        PagoResponseDTO response = pagoService.buscarPagoPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PAGADO", response.getEstadoPago());
        verify(pagoRepository, times(1)).findById(1L);
    }

    @Test
    void deberiaEliminarPagoCambiandoActivoAFalse() {

        // Arrange: se simula un pago activo
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(1L);
        pago.setMonto(new BigDecimal("25000"));
        pago.setMetodoPago("TARJETA");
        pago.setEstadoPago("PAGADO");
        pago.setFechaPago(LocalDateTime.now());
        pago.setActivo(true);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        // Act
        pagoService.eliminarPago(1L);

        // Assert
        assertFalse(pago.getActivo());
        verify(pagoRepository, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(pago);
    }
}
