package es.arbox.eGestion.dao.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.SubtiposImporte;

public interface SubtiposImporteDAO {
	public List<SubtiposImporte> getSubtiposImporte(Integer idTipo);
}
