package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Calendario;
import es.arbox.eGestion.service.config.GenericService;

public interface CalendarioService extends GenericService {
	public List<Calendario> getCalendarios(Calendario calendario);
}
