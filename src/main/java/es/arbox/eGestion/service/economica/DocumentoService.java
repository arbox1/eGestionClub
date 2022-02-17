package es.arbox.eGestion.service.economica;

import java.util.List;

import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.service.config.GenericService;

public interface DocumentoService extends GenericService {

	public List<Documento> getBusqueda(Documento documento);
	
	public List<TipoDocumento> getTiposDocumento(FamiliasDocumento familia);
}
