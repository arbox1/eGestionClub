package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.IngresosGastos;
import es.arbox.eGestion.service.config.GenericService;

public interface IngresosGastosService extends GenericService {

	public List<IngresosGastos> getBusqueda(IngresosGastos ingresosGastos);
}
