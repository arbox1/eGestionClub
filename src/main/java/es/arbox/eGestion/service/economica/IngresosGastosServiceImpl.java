package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.economica.IngresosGastosDAO;
import es.arbox.eGestion.entity.economica.IngresosGastos;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class IngresosGastosServiceImpl extends GenericServiceImpl implements IngresosGastosService {

	@Autowired
	private IngresosGastosDAO ingresosGastosDAO;
	
	@Override
	@Transactional
	public List<IngresosGastos> getBusqueda(IngresosGastos ingresosGastos){
		return ingresosGastosDAO.getBusqueda(ingresosGastos);
	}
}
