package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Socios;
import es.arbox.eGestion.service.config.GenericService;

public interface SociosService extends GenericService {
	public List<Socios> getSocios();
	public List<Socios> getBusqueda(Socios socio);
}
