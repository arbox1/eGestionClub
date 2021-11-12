package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.CalendarioDAO;
import es.arbox.eGestion.entity.socios.Calendario;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class CalendarioServiceImpl extends GenericServiceImpl implements CalendarioService {

	@Autowired
	private CalendarioDAO calendarioDAO;
	
	@Override
	@Transactional
	public List<Calendario> getCalendarios(Calendario calendario) {
		return calendarioDAO.getCalendarios(calendario);
	}

}
