package com.clinica.ms_notificaciones.repository;

import com.clinica.ms_notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByReservaId(Long reservaId);

    List<Notificacion> findByEstado(String estado);

    List<Notificacion> findByTipoNotificacion(String tipoNotificacion);
}
