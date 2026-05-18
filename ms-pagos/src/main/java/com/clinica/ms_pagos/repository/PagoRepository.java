package com.clinica.ms_pagos.repository;

import com.clinica.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByReservaId(Long reservaId);

    List<Pago> findByEstadoPago(String estadoPago);

    boolean existsByReservaIdAndActivoTrue(Long reservaId);
}
