package es.arbox.eGestion.service.actividades;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.actividades.DocumentoParticipanteDAO;
import es.arbox.eGestion.dao.documento.DocumentoDAO;
import es.arbox.eGestion.entity.actividades.DocumentoParticipante;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class DocumentoParticipanteServiceImpl extends GenericServiceImpl implements DocumentoParticipanteService {
	
	@Autowired
	private DocumentoParticipanteDAO documentoParticipanteDAO;
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Override
	@Transactional
	public List<DocumentoParticipante> getDocumentos(Integer idActividad) {
		return documentoParticipanteDAO.getDocumentos(idActividad);
	}
	
	
	@Override
	@Transactional
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia) {
		return documentoDAO.getTiposDocumento(familia);
	}
	
	@Override
	@Transactional
	public List<DocumentoParticipante> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento) {
		return documentoParticipanteDAO.getDocumentosPorTipo(idActividad, tipoDocumento);
	}
	
	@Override
	@Transactional
	public DocumentoParticipante getDocumentoParticipante(Integer idDocumento) {
		return documentoParticipanteDAO.getDocumentoParticipante(idDocumento);
	}
}
