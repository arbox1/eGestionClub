package es.arbox.eGestion.dao.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.IngresosGastos;

public interface IngresosGastosDAO {

	public List<IngresosGastos> getBusqueda(IngresosGastos ingresosGastos);
}
