package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.entity.economica.DocumentoIngresoGasto;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.service.config.GenericService;

public interface DocumentoIngresoGastoService extends GenericService {
	
	public List<DocumentoIngresoGasto> getDocumentos(Integer idIngresoGasto	);
	
	public List<TipoDocumento> getTipoDocumento(FamiliasDocumento familia);
}
