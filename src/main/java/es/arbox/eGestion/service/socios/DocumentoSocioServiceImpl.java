package es.arbox.eGestion.service.socios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.socios.DocumentoSocioDAO;
import es.arbox.eGestion.entity.socios.DocumentoSocio;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class DocumentoSocioServiceImpl extends GenericServiceImpl implements DocumentoSocioService {
	
	@Autowired
	private DocumentoSocioDAO documentoSocioDAO;
	
	@Override
	@Transactional
	public List<DocumentoSocio> getDocumentos(Integer idSocio){
		return documentoSocioDAO.getDocumentos(idSocio);
	}
	
	@Override
	@Transactional
	public DocumentoSocio getDocumentoFoto(Integer idSocio){
		return documentoSocioDAO.getDocumentoFoto(idSocio);
	}
}
