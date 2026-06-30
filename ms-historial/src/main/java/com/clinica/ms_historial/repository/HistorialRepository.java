package com.clinica.ms_historial.repository;

import com.clinica.ms_historial.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {

    List<Historial> findByPacienteId(Long pacienteId);

    List<Historial> findByDoctorId(Long doctorId);

    List<Historial> findByReservaId(Long reservaId);
}
