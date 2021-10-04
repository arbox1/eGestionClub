package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.Pago;
import es.arbox.eGestion.service.config.GenericService;

public interface PagosService extends GenericService {
	
	public List<Pago> getBusqueda(Pago pago);
}
