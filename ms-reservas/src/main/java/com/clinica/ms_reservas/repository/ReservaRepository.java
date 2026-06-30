package com.clinica.ms_reservas.repository;

import com.clinica.ms_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByPacienteId(Long pacienteId);

    List<Reserva> findByDoctorId(Long doctorId);

    List<Reserva> findByEstado(String estado);

    boolean existsByDoctorIdAndFechaReservaAndHoraReservaAndActivoTrue(Long doctorId, LocalDate fechaReserva, LocalTime horaReserva);

    boolean existsByPacienteIdAndFechaReservaAndHoraReservaAndActivoTrue(Long pacienteId, LocalDate fechaReserva, LocalTime horaReserva);
}
