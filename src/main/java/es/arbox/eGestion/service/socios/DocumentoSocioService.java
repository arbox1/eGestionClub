package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.DocumentoSocio;
import es.arbox.eGestion.service.config.GenericService;

public interface DocumentoSocioService extends GenericService {
	
	public List<DocumentoSocio> getDocumentos(Integer idSocio);
	
	public DocumentoSocio getDocumentoFoto(Integer idSocio);
}
