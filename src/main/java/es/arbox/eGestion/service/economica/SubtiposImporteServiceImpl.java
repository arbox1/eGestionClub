package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.economica.SubtiposImporteDAO;
import es.arbox.eGestion.entity.economica.SubtiposImporte;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class SubtiposImporteServiceImpl extends GenericServiceImpl implements SubtiposImporteService{
	
	@Autowired
	private SubtiposImporteDAO subtiposImporteDAO;
	
	@Override
	@Transactional
	public List<SubtiposImporte> getSubtiposImporte(Integer idTipo) {
		return subtiposImporteDAO.getSubtiposImporte(idTipo);
	}
}
