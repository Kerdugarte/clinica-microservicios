package com.clinica.ms_notificaciones.service;

import com.clinica.ms_notificaciones.client.ReservaClient;
import com.clinica.ms_notificaciones.dto.NotificacionRequestDTO;
import com.clinica.ms_notificaciones.dto.NotificacionResponseDTO;
import com.clinica.ms_notificaciones.exception.ResourceNotFoundException;
import com.clinica.ms_notificaciones.model.Notificacion;
import com.clinica.ms_notificaciones.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository repository;
    private final ReservaClient reservaClient;

    public NotificacionServiceImpl(NotificacionRepository repository, ReservaClient reservaClient) {
        this.repository = repository;
        this.reservaClient = reservaClient;
    }

    @Override
    public NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO requestDTO) {
        log.info("Creando notificacion para reserva ID: {}", requestDTO.getReservaId());

        validarReserva(requestDTO.getReservaId());

        Notificacion notificacion = new Notificacion();
        notificacion.setReservaId(requestDTO.getReservaId());
        notificacion.setDestinatario(requestDTO.getDestinatario());
        notificacion.setTipoNotificacion(requestDTO.getTipoNotificacion());
        notificacion.setMensaje(requestDTO.getMensaje());
        notificacion.setEstado(requestDTO.getEstado() != null && !requestDTO.getEstado().isBlank()
                ? requestDTO.getEstado()
                : "PENDIENTE");
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setActivo(true);

        Notificacion guardada = repository.save(notificacion);
        log.info("Notificacion creada correctamente con ID: {}", guardada.getId());

        return convertirAResponseDTO(guardada);
    }

    @Override
    public List<NotificacionResponseDTO> listarNotificaciones() {
        log.info("Listando notificaciones");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionResponseDTO buscarNotificacionPorId(Long id) {
        log.info("Buscando notificacion por ID: {}", id);
        Notificacion notificacion = obtenerNotificacionPorId(id);
        return convertirAResponseDTO(notificacion);
    }

    @Override
    public List<NotificacionResponseDTO> buscarNotificacionesPorReserva(Long reservaId) {
        log.info("Buscando notificaciones por reserva ID: {}", reservaId);
        return repository.findByReservaId(reservaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponseDTO> buscarNotificacionesPorEstado(String estado) {
        log.info("Buscando notificaciones por estado: {}", estado);
        return repository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponseDTO> buscarNotificacionesPorTipo(String tipoNotificacion) {
        log.info("Buscando notificaciones por tipo: {}", tipoNotificacion);
        return repository.findByTipoNotificacion(tipoNotificacion)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionResponseDTO actualizarNotificacion(Long id, NotificacionRequestDTO requestDTO) {
        log.info("Actualizando notificacion con ID: {}", id);
        Notificacion notificacion = obtenerNotificacionPorId(id);

        validarReserva(requestDTO.getReservaId());

        notificacion.setReservaId(requestDTO.getReservaId());
        notificacion.setDestinatario(requestDTO.getDestinatario());
        notificacion.setTipoNotificacion(requestDTO.getTipoNotificacion());
        notificacion.setMensaje(requestDTO.getMensaje());

        if (requestDTO.getEstado() != null && !requestDTO.getEstado().isBlank()) {
            notificacion.setEstado(requestDTO.getEstado());
        }

        Notificacion actualizada = repository.save(notificacion);
        log.info("Notificacion actualizada correctamente con ID: {}", actualizada.getId());

        return convertirAResponseDTO(actualizada);
    }

    @Override
    public NotificacionResponseDTO cambiarEstadoNotificacion(Long id, String estado) {
        log.info("Cambiando estado de notificacion ID: {} a {}", id, estado);
        Notificacion notificacion = obtenerNotificacionPorId(id);
        notificacion.setEstado(estado);
        Notificacion actualizada = repository.save(notificacion);
        return convertirAResponseDTO(actualizada);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        log.info("Eliminando logicamente notificacion con ID: {}", id);
        Notificacion notificacion = obtenerNotificacionPorId(id);
        notificacion.setActivo(false);
        repository.save(notificacion);
        log.info("Notificacion desactivada correctamente con ID: {}", id);
    }

    private void validarReserva(Long reservaId) {
        try {
            reservaClient.buscarReservaPorId(reservaId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No existe una reserva activa con ID: " + reservaId);
        }
    }

    private Notificacion obtenerNotificacionPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada con ID: " + id));
    }

    private NotificacionResponseDTO convertirAResponseDTO(Notificacion notificacion) {
        return new NotificacionResponseDTO(
                notificacion.getId(),
                notificacion.getReservaId(),
                notificacion.getDestinatario(),
                notificacion.getTipoNotificacion(),
                notificacion.getMensaje(),
                notificacion.getEstado(),
                notificacion.getFechaEnvio(),
                notificacion.getActivo()
        );
    }
}
