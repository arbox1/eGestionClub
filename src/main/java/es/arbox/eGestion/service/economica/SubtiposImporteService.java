package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.SubtiposImporte;
import es.arbox.eGestion.service.config.GenericService;

public interface SubtiposImporteService extends GenericService {
	public List<SubtiposImporte> getSubtiposImporte(Integer idTipo);
}
