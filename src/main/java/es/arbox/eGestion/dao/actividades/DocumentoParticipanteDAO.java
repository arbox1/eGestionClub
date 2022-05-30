package es.arbox.eGestion.dao.actividades;

import java.util.List;

import es.arbox.eGestion.entity.actividades.DocumentoParticipante;
import es.arbox.eGestion.enums.TiposDocumento;

public interface DocumentoParticipanteDAO {

	public List<DocumentoParticipante> getDocumentos(Integer idActividad);
	
	public List<DocumentoParticipante> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento);
	
	public DocumentoParticipante getDocumentoParticipante(Integer idDocumento);
}
