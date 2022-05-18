package es.arbox.eGestion.service.actividades;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.actividades.DocumentoActividadDAO;
import es.arbox.eGestion.dao.documento.DocumentoDAO;
import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class DocumentoActividadServiceImpl extends GenericServiceImpl implements DocumentoActividadService {
	
	@Autowired
	private DocumentoActividadDAO documentoActividadDAO;
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Override
	@Transactional
	public List<DocumentoActividad> getDocumentos(Integer idActividad) {
		return documentoActividadDAO.getDocumentos(idActividad);
	}
	
	
	@Override
	@Transactional
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia) {
		return documentoDAO.getTiposDocumento(familia);
	}
	
	@Override
	@Transactional
	public List<DocumentoActividad> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento) {
		return documentoActividadDAO.getDocumentosPorTipo(idActividad, tipoDocumento);
	}
	
	@Override
	@Transactional
	public DocumentoActividad getDocumentoActividad(Integer idDocumento) {
		return documentoActividadDAO.getDocumentoActividad(idDocumento);
	}
}
