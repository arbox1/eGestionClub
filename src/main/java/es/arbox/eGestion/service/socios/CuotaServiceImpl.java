package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.CuotaDAO;
import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class CuotaServiceImpl extends GenericServiceImpl implements CuotaService {

	@Autowired
	private CuotaDAO CuotaDAO;
	
	@Override
	@Transactional
	public List<Cuota> getCuotas(Integer idSocioCurso) {
		return CuotaDAO.getCuotas(idSocioCurso);
	}

}
