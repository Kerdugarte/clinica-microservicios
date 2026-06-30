package com.clinica.ms_pacientes.repository;

import com.clinica.ms_pacientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);

    Optional<Paciente> findByEmail(String email);

    boolean existsByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByRutAndActivoTrue(String rut);

    boolean existsByEmailAndActivoTrue(String email);
}
