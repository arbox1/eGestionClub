package es.arbox.eGestion.dao.comunicacion;

import java.util.List;

import es.arbox.eGestion.entity.comunicacion.Noticia;

public interface NoticiaDAO {

	public List<Noticia> getNoticiasFiltro(Noticia noticia);
}
