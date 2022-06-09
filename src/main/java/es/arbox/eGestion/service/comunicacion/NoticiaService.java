package es.arbox.eGestion.service.comunicacion;

import java.util.List;

import es.arbox.eGestion.entity.comunicacion.Noticia;
import es.arbox.eGestion.service.config.GenericService;

public interface NoticiaService extends GenericService {
	
	public List<Noticia> getNoticiasFiltro(Noticia noticia);
}
