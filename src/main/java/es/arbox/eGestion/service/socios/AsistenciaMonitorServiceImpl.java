package es.arbox.eGestion.service.socios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.AsistenciaMonitorDAO;
import es.arbox.eGestion.dto.ResumenAsistenciaDTO;
import es.arbox.eGestion.dto.ResumenAsistenciaDTO.AsistenciaMes;
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

	@Override
	@Transactional
	public List<ResumenAsistenciaDTO> getResumenAsistencia(AsistenciaMonitor asistencia, String[] MES) {
		List<AsistenciaMonitor> asistenciasBd = asistenciaMonitorDAO.getAsistencias(asistencia);

		SortedMap<Integer, ResumenAsistenciaDTO> asistencias = new TreeMap<>();
		
		for(AsistenciaMonitor asistenciaBd : asistenciasBd) {
			ResumenAsistenciaDTO resumen = asistencias.get(asistenciaBd.getMonitor().getId()) != null ? asistencias.get(asistenciaBd.getMonitor().getId()) : new ResumenAsistenciaDTO();
			
			resumen.setMonitor(asistenciaBd.getMonitor());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(asistenciaBd.getFecha());
			Integer mes = cal.get(Calendar.MONTH);
			
			AsistenciaMes asistenciaMes = resumen.getAsistencias().get(mes) != null ? resumen.getAsistencias().get(mes) : new AsistenciaMes();
			asistenciaMes.setMes(MES[mes]);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			asistenciaMes.setAnyo(Integer.valueOf(format.format(asistenciaBd.getFecha())));
			asistenciaMes.setImporte(asistenciaMes.getImporte() == null
					? (asistenciaBd.getHoras() * asistenciaBd.getTarifa().getImporte())
					: asistenciaMes.getImporte()+(asistenciaBd.getHoras() * asistenciaBd.getTarifa().getImporte()));
			
			resumen.getAsistencias().put(mes, asistenciaMes);

			resumen.setTotal(resumen.getTotal() != null ?
								resumen.getTotal() + (asistenciaBd.getHoras() * asistenciaBd.getTarifa().getImporte()) : 
								asistenciaBd.getHoras() * asistenciaBd.getTarifa().getImporte());
			
			asistencias.put(asistenciaBd.getMonitor().getId(), resumen);
		}
		
		List<ResumenAsistenciaDTO> resultado = new ArrayList<>();
		for(Integer key : asistencias.keySet()) {
			resultado.add(asistencias.get(key));
		}
		
		return resultado;
	}

}
