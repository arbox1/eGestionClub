package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.TiposImporte;
import es.arbox.eGestion.service.config.GenericService;

public interface TiposImporteService extends GenericService{
	public List<TiposImporte> getTiposImporte();
}
