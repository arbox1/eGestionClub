package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.SociosDAO;
import es.arbox.eGestion.entity.socios.Socios;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class SociosServiceImpl extends GenericServiceImpl implements SociosService {

	@Autowired
	private SociosDAO sociosDAO;
	
	@Override
	@Transactional
	public List<Socios> getSocios() {
		return sociosDAO.getSocios();
	}
	
	@Override
	@Transactional
	public List<Socios> getBusqueda(Socios socio) {
		return sociosDAO.getBusqueda(socio);
	}
}
