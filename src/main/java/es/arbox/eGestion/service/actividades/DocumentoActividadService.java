package es.arbox.eGestion.service.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.service.config.GenericService;

public interface DocumentoActividadService extends GenericService {
	
	public List<DocumentoActividad> getDocumentos(Integer idActividad);
	
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia);
	
	public List<DocumentoActividad> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento);
	
	public DocumentoActividad getDocumentoActividad(Integer idDocumento);
}
