package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.documento.DocumentoDAO;
import es.arbox.eGestion.dao.economica.DocumentoIngresoGastoDAO;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.entity.economica.DocumentoIngresoGasto;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class DocumentoIngresoGastoServiceImpl extends GenericServiceImpl implements DocumentoIngresoGastoService {
	
	@Autowired
	private DocumentoIngresoGastoDAO documentoIngresoGastoDAO;
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Override
	@Transactional
	public List<DocumentoIngresoGasto> getDocumentos(Integer idIngresoGasto) {
		return documentoIngresoGastoDAO.getDocumentos(idIngresoGasto);
	}
	
	
	@Override
	@Transactional
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia) {
		return documentoDAO.getTiposDocumento(familia);
	}
}
