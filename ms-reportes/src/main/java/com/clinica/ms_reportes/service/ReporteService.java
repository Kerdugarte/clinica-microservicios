package com.clinica.ms_reportes.service;

import com.clinica.ms_reportes.dto.ReporteDetalleDTO;
import com.clinica.ms_reportes.dto.ReporteResumenDTO;

public interface ReporteService {

    ReporteResumenDTO obtenerResumenGeneral();

    ReporteDetalleDTO obtenerReportePaciente(Long pacienteId);

    ReporteDetalleDTO obtenerReporteDoctor(Long doctorId);

    ReporteDetalleDTO obtenerReporteReserva(Long reservaId);
}
