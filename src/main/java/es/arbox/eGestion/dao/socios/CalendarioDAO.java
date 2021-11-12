package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Calendario;

public interface CalendarioDAO {

	public List<Calendario> getCalendarios(Calendario calendario);
}
