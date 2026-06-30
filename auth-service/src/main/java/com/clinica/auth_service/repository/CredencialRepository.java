package com.clinica.auth_service.repository;

import com.clinica.auth_service.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {

    Optional<Credencial> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndActivoTrue(String email);
}
