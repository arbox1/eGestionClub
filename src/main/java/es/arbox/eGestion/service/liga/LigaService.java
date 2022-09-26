package es.arbox.eGestion.service.liga;

import java.util.List;

import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.service.config.GenericService;

public interface LigaService extends GenericService {
	
	public List<Liga> getLigasFiltro(Liga liga);
}
