package es.arbox.eGestion.service.liga;

import java.util.List;

import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;
import es.arbox.eGestion.service.config.GenericService;

public interface LigaService extends GenericService {
	
	public List<Liga> getLigasFiltro(Liga liga);
	public List<Grupo> getDatosLiga(Liga liga);
	public CalendarioLiga getCalendarioLiga(CalendarioLiga calendario);
	public List<Resultado> getResultadosEquipo(Equipo equipo);
}
