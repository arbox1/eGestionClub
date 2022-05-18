package es.arbox.eGestion.service.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.service.config.GenericService;

public interface ActividadService extends GenericService {
	
	public List<Actividad> getActividadesFiltro(Actividad actividad);
	
	public List<Participante> getParticipantes(Integer idActividad);
	
	public Integer getInscritos(Integer idActividad);
	
	public Participante getParticipantePassword(Participante participante);
}
