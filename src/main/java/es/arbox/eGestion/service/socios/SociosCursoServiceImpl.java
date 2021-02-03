package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.SociosCursoDAO;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class SociosCursoServiceImpl extends GenericServiceImpl implements SociosCursoService {

	@Autowired
	private SociosCursoDAO sociosCursoDAO;
	
	@Override
	@Transactional
	public List<SociosCurso> getSociosCurso(Integer idSocio) {
		return sociosCursoDAO.getSociosCurso(idSocio);
	}

//	@Override
//	public List<Curso> getCursos() {
//		return sociosCursoDAO.getCursos();
//	}
//
//	@Override
//	public List<Escuela> getEscuelas() {
//		return sociosCursoDAO.getEscuelas();
//	}
//
//	@Override
//	public List<Categoria> getCategorias() {
//		return sociosCursoDAO.getCategorias();
//	}
//
//	@Override
//	public List<Meses> getMeses() {
//		return sociosCursoDAO.getMeses();
//	}

}
