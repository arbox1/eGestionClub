package es.arbox.eGestion.service.liga;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.liga.LigaDAO;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class LigaServiceImpl extends GenericServiceImpl implements LigaService {
	
	@Autowired
	private LigaDAO ligaDAO;

	@Override
	@Transactional
	public List<Liga> getLigasFiltro(Liga liga){
		return ligaDAO.getLigasFiltro(liga);
	}
}
