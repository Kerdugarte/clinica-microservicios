package com.clinica.ms_notificaciones.service;

import com.clinica.ms_notificaciones.dto.NotificacionRequestDTO;
import com.clinica.ms_notificaciones.dto.NotificacionResponseDTO;

import java.util.List;

public interface NotificacionService {

    NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO requestDTO);

    List<NotificacionResponseDTO> listarNotificaciones();

    NotificacionResponseDTO buscarNotificacionPorId(Long id);

    List<NotificacionResponseDTO> buscarNotificacionesPorReserva(Long reservaId);

    List<NotificacionResponseDTO> buscarNotificacionesPorEstado(String estado);

    List<NotificacionResponseDTO> buscarNotificacionesPorTipo(String tipoNotificacion);

    NotificacionResponseDTO actualizarNotificacion(Long id, NotificacionRequestDTO requestDTO);

    NotificacionResponseDTO cambiarEstadoNotificacion(Long id, String estado);

    void eliminarNotificacion(Long id);
}
