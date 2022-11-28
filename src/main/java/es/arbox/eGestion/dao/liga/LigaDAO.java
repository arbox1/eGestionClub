package es.arbox.eGestion.dao.liga;

import java.util.List;

import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Jornada;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;

public interface LigaDAO {

	public List<Liga> getLigasFiltro(Liga liga);

	public List<Grupo> getGrupos(Liga liga);
	public List<Jornada> getJornadas(Grupo grupo);
	public List<CalendarioLiga> getCalendarios(Jornada jornada);
	public List<Resultado> getResultados(CalendarioLiga calendario);
}
