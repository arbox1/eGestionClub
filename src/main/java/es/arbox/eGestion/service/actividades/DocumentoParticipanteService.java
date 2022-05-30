package es.arbox.eGestion.service.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.DocumentoParticipante;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.service.config.GenericService;

public interface DocumentoParticipanteService extends GenericService {
	
	public List<DocumentoParticipante> getDocumentos(Integer idActividad);
	
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia);
	
	public List<DocumentoParticipante> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento);
	
	public DocumentoParticipante getDocumentoParticipante(Integer idDocumento);
}
