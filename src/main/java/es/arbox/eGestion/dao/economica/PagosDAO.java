package es.arbox.eGestion.dao.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.Pago;

public interface PagosDAO {
	
	public List<Pago> getBusqueda(Pago pago);
}
