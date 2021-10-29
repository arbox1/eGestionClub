package es.arbox.eGestion.service.actividades;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.actividades.ActividadDAO;
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class ActividadServiceImpl extends GenericServiceImpl implements ActividadService {

	@Autowired
	private ActividadDAO actividadDAO;
	
	@Override
	@Transactional
	public List<Actividad> getActividadesFiltro(Actividad actividad){
		return actividadDAO.getActividadesFiltro(actividad);
	}
	
	@Override
	@Transactional
	public List<Participante> getParticipantes(Integer idActividad){
		return actividadDAO.getParticipantes(idActividad);
	}
	
	@Override
	@Transactional
	public Integer getInscritos(Integer idActividad) {
		return actividadDAO.getInscritos(idActividad);
	}
}
