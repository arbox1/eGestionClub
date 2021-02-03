package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.economica.TiposImporteDAO;
import es.arbox.eGestion.entity.economica.TiposImporte;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class TiposImporteServiceImpl extends GenericServiceImpl implements TiposImporteService{
	
	@Autowired
	private TiposImporteDAO tiposImporteDAO;
	
	@Override
	@Transactional
	public List<TiposImporte> getTiposImporte() {
		return tiposImporteDAO.getTiposImporte();
	}
}
