
package com.clinica.ms_pagos.controller;

import com.clinica.ms_pagos.dto.PagoRequestDTO;
import com.clinica.ms_pagos.dto.PagoResponseDTO;
import com.clinica.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Pagos", description = "Endpoints para la gestión de pagos de la clínica")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Crear pago", description = "Registra un nuevo pago asociado a una reserva médica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o pago no permitido"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud para crear pago de reserva ID: {}", requestDTO.getReservaId());
        PagoResponseDTO response = pagoService.crearPago(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar pagos", description = "Obtiene el listado completo de pagos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listarPagos() {
        log.info("Solicitud para listar pagos");
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @Operation(summary = "Buscar pago por ID", description = "Obtiene la información de un pago mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> buscarPagoPorId(
            @Parameter(description = "Identificador del pago", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para buscar pago por ID: {}", id);
        return ResponseEntity.ok(pagoService.buscarPagoPorId(id));
    }

    @Operation(summary = "Buscar pagos por reserva", description = "Obtiene todos los pagos asociados a una reserva médica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos de la reserva obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<PagoResponseDTO>> buscarPagosPorReserva(
            @Parameter(description = "Identificador de la reserva", example = "1")
            @PathVariable Long reservaId) {
        log.info("Solicitud para buscar pagos por reserva ID: {}", reservaId);
        return ResponseEntity.ok(pagoService.buscarPagosPorReserva(reservaId));
    }

    @Operation(summary = "Buscar pagos por estado", description = "Obtiene los pagos filtrados por estado.")
    @ApiResponse(responseCode = "200", description = "Pagos filtrados correctamente")
    @GetMapping("/estado")
    public ResponseEntity<List<PagoResponseDTO>> buscarPagosPorEstado(
            @Parameter(description = "Estado del pago", example = "PENDIENTE")
            @RequestParam String estadoPago) {
        log.info("Solicitud para buscar pagos por estado: {}", estadoPago);
        return ResponseEntity.ok(pagoService.buscarPagosPorEstado(estadoPago));
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizarPago(
            @Parameter(description = "Identificador del pago", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO requestDTO) {
        log.info("Solicitud para actualizar pago con ID: {}", id);
        return ResponseEntity.ok(pagoService.actualizarPago(id, requestDTO));
    }

    @Operation(summary = "Cambiar estado de pago", description = "Actualiza únicamente el estado de un pago.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> cambiarEstadoPago(
            @Parameter(description = "Identificador del pago", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pago", example = "PAGADO")
            @RequestParam String estadoPago) {
        log.info("Solicitud para cambiar estado de pago ID: {} a {}", id, estadoPago);
        return ResponseEntity.ok(pagoService.cambiarEstadoPago(id, estadoPago));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina lógicamente un pago mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(
            @Parameter(description = "Identificador del pago", example = "1")
            @PathVariable Long id) {
        log.info("Solicitud para eliminar logicamente pago con ID: {}", id);
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
