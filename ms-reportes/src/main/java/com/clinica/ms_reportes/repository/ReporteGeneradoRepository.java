package com.clinica.ms_reportes.repository;

import com.clinica.ms_reportes.model.ReporteGenerado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteGeneradoRepository extends JpaRepository<ReporteGenerado, Long> {

    List<ReporteGenerado> findByTipoReporte(String tipoReporte);

    List<ReporteGenerado> findByReferenciaId(Long referenciaId);
}
