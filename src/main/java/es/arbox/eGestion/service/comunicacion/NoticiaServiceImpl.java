package es.arbox.eGestion.service.comunicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.comunicacion.NoticiaDAO;
import es.arbox.eGestion.entity.comunicacion.Noticia;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class NoticiaServiceImpl extends GenericServiceImpl implements NoticiaService {


	@Autowired
	private NoticiaDAO noticiaDAO;
	
	@Override
	@Transactional
	public List<Noticia> getNoticiasFiltro(Noticia noticia) {
		return noticiaDAO.getNoticiasFiltro(noticia);
	}
}
