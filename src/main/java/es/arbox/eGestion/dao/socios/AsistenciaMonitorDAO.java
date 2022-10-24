package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.AsistenciaMonitor;

public interface AsistenciaMonitorDAO {
	public List<AsistenciaMonitor> getAsistencias(AsistenciaMonitor aistencia);
}
