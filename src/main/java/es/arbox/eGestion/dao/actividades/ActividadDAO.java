package es.arbox.eGestion.dao.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.Participante;

public interface ActividadDAO {
	public List<Actividad> getActividadesFiltro(Actividad actividad);
	
	public List<Participante> getParticipantes(Integer idActividad);
	
	public Integer getInscritos(Integer idActividad);
}
