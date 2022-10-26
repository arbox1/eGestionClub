package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.dto.ResumenAsistenciaDTO;
import es.arbox.eGestion.entity.socios.AsistenciaMonitor;
import es.arbox.eGestion.service.config.GenericService;

public interface AsistenciaMonitorService extends GenericService {
	public List<AsistenciaMonitor> getAsistencias(AsistenciaMonitor aistencia);
	
	public List<ResumenAsistenciaDTO> getResumenAsistencia(AsistenciaMonitor asistencia, String[] MES);
}
