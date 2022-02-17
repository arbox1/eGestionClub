package es.arbox.eGestion.service.economica;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.documento.DocumentoDAO;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class DocumentoServiceImpl extends GenericServiceImpl implements DocumentoService {
	
	@Autowired
	private DocumentoDAO documentosDAO;
	
	@Override
	@Transactional
	public List<Documento> getBusqueda(Documento documento){
		return documentosDAO.getBusqueda(documento);
	}
	
	@Override
	@Transactional
	public List<TipoDocumento> getTiposDocumento(FamiliasDocumento familia) {
		return documentosDAO.getTiposDocumento(familia);
	}
}
