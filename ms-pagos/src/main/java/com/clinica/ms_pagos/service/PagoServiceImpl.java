package com.clinica.ms_pagos.service;

import com.clinica.ms_pagos.client.ReservaClient;
import com.clinica.ms_pagos.dto.PagoRequestDTO;
import com.clinica.ms_pagos.dto.PagoResponseDTO;
import com.clinica.ms_pagos.exception.ResourceNotFoundException;
import com.clinica.ms_pagos.model.Pago;
import com.clinica.ms_pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);

    private final PagoRepository repository;
    private final ReservaClient reservaClient;

    public PagoServiceImpl(PagoRepository repository, ReservaClient reservaClient) {
        this.repository = repository;
        this.reservaClient = reservaClient;
    }

    @Override
    public PagoResponseDTO crearPago(PagoRequestDTO requestDTO) {
        log.info("Creando pago para reserva ID: {}", requestDTO.getReservaId());

        validarReserva(requestDTO.getReservaId());

        if (repository.existsByReservaIdAndActivoTrue(requestDTO.getReservaId())) {
            throw new IllegalArgumentException("Ya existe un pago activo asociado a esta reserva");
        }

        Pago pago = new Pago();
        pago.setReservaId(requestDTO.getReservaId());
        pago.setMonto(requestDTO.getMonto());
        pago.setMetodoPago(requestDTO.getMetodoPago());
        pago.setEstadoPago(requestDTO.getEstadoPago() != null && !requestDTO.getEstadoPago().isBlank()
                ? requestDTO.getEstadoPago()
                : "PENDIENTE");
        pago.setFechaPago(LocalDateTime.now());
        pago.setActivo(true);

        Pago guardado = repository.save(pago);
        log.info("Pago creado correctamente con ID: {}", guardado.getId());

        return convertirAResponseDTO(guardado);
    }

    @Override
    public List<PagoResponseDTO> listarPagos() {
        log.info("Listando pagos");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponseDTO buscarPagoPorId(Long id) {
        log.info("Buscando pago por ID: {}", id);
        Pago pago = obtenerPagoPorId(id);
        return convertirAResponseDTO(pago);
    }

    @Override
    public List<PagoResponseDTO> buscarPagosPorReserva(Long reservaId) {
        log.info("Buscando pagos por reserva ID: {}", reservaId);
        return repository.findByReservaId(reservaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoResponseDTO> buscarPagosPorEstado(String estadoPago) {
        log.info("Buscando pagos por estado: {}", estadoPago);
        return repository.findByEstadoPago(estadoPago)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponseDTO actualizarPago(Long id, PagoRequestDTO requestDTO) {
        log.info("Actualizando pago con ID: {}", id);
        Pago pago = obtenerPagoPorId(id);

        validarReserva(requestDTO.getReservaId());

        if (!pago.getReservaId().equals(requestDTO.getReservaId())
                && repository.existsByReservaIdAndActivoTrue(requestDTO.getReservaId())) {
            throw new IllegalArgumentException("Ya existe un pago activo asociado a esta reserva");
        }

        pago.setReservaId(requestDTO.getReservaId());
        pago.setMonto(requestDTO.getMonto());
        pago.setMetodoPago(requestDTO.getMetodoPago());

        if (requestDTO.getEstadoPago() != null && !requestDTO.getEstadoPago().isBlank()) {
            pago.setEstadoPago(requestDTO.getEstadoPago());
        }

        Pago actualizado = repository.save(pago);
        log.info("Pago actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponseDTO(actualizado);
    }

    @Override
    public PagoResponseDTO cambiarEstadoPago(Long id, String estadoPago) {
        log.info("Cambiando estado de pago ID: {} a {}", id, estadoPago);
        Pago pago = obtenerPagoPorId(id);
        pago.setEstadoPago(estadoPago);
        Pago actualizado = repository.save(pago);
        return convertirAResponseDTO(actualizado);
    }

    @Override
    public void eliminarPago(Long id) {
        log.info("Eliminando logicamente pago con ID: {}", id);
        Pago pago = obtenerPagoPorId(id);
        pago.setActivo(false);
        repository.save(pago);
        log.info("Pago desactivado correctamente con ID: {}", id);
    }

    private void validarReserva(Long reservaId) {
        try {
            reservaClient.buscarReservaPorId(reservaId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No existe una reserva activa con ID: " + reservaId);
        }
    }

    private Pago obtenerPagoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
    }

    private PagoResponseDTO convertirAResponseDTO(Pago pago) {
        return new PagoResponseDTO(
                pago.getId(),
                pago.getReservaId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstadoPago(),
                pago.getFechaPago(),
                pago.getActivo()
        );
    }
}
