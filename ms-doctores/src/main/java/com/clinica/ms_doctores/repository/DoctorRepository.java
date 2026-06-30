package com.clinica.ms_doctores.repository;

import com.clinica.ms_doctores.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByRut(String rut);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByNumeroRegistro(String numeroRegistro);

    boolean existsByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByNumeroRegistro(String numeroRegistro);

    boolean existsByRutAndActivoTrue(String rut);

    boolean existsByEmailAndActivoTrue(String email);

    boolean existsByNumeroRegistroAndActivoTrue(String numeroRegistro);
}
