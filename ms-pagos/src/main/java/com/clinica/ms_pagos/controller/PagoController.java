
package com.clinica.ms_pagos.controller;

import com.clinica.ms_pagos.dto.PagoRequestDTO;
import com.clinica.ms_pagos.dto.PagoResponseDTO;
import com.clinica.ms_pagos.service.PagoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud para crear pago de reserva ID: {}", requestDTO.getReservaId());
        PagoResponseDTO response = pagoService.crearPago(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listarPagos() {
        log.info("Solicitud para listar pagos");
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> buscarPagoPorId(@PathVariable Long id) {
        log.info("Solicitud para buscar pago por ID: {}", id);
        return ResponseEntity.ok(pagoService.buscarPagoPorId(id));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<PagoResponseDTO>> buscarPagosPorReserva(@PathVariable Long reservaId) {
        log.info("Solicitud para buscar pagos por reserva ID: {}", reservaId);
        return ResponseEntity.ok(pagoService.buscarPagosPorReserva(reservaId));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<PagoResponseDTO>> buscarPagosPorEstado(@RequestParam String estadoPago) {
        log.info("Solicitud para buscar pagos por estado: {}", estadoPago);
        return ResponseEntity.ok(pagoService.buscarPagosPorEstado(estadoPago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizarPago(
            @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud para actualizar pago con ID: {}", id);
        return ResponseEntity.ok(pagoService.actualizarPago(id, requestDTO));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> cambiarEstadoPago(
            @PathVariable Long id,
            @RequestParam String estadoPago) {
        log.info("Solicitud para cambiar estado de pago ID: {} a {}", id, estadoPago);
        return ResponseEntity.ok(pagoService.cambiarEstadoPago(id, estadoPago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente pago con ID: {}", id);
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
