package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.AsistenciaMonitorDAO;
import es.arbox.eGestion.entity.socios.AsistenciaMonitor;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class AsistenciaMonitorServiceImpl extends GenericServiceImpl implements AsistenciaMonitorService {

	@Autowired
	private AsistenciaMonitorDAO asistenciaMonitorDAO;
	
	@Override
	@Transactional
	public List<AsistenciaMonitor> getAsistencias(AsistenciaMonitor aistencia){
		return asistenciaMonitorDAO.getAsistencias(aistencia);
	}

}
