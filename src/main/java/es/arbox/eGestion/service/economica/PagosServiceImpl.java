package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.economica.PagosDAO;
import es.arbox.eGestion.entity.economica.Pago;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class PagosServiceImpl extends GenericServiceImpl implements PagosService {
	
	@Autowired
	private PagosDAO pagosDAO;
	
	@Override
	@Transactional
	public List<Pago> getBusqueda(Pago pago){
		return pagosDAO.getBusqueda(pago);
	}
}
