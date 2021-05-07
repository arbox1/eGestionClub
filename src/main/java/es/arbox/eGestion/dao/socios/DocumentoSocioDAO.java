package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.DocumentoSocio;

public interface DocumentoSocioDAO {

	public List<DocumentoSocio> getDocumentos(Integer idSocio);
}
