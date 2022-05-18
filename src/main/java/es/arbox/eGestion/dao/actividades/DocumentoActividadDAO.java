package es.arbox.eGestion.dao.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.enums.TiposDocumento;

public interface DocumentoActividadDAO {

	public List<DocumentoActividad> getDocumentos(Integer idActividad);
	
	public List<DocumentoActividad> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento);
	
	public DocumentoActividad getDocumentoActividad(Integer idDocumento);
}
