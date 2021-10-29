package es.arbox.eGestion.dao.documento;

import java.util.List;

import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;

public interface DocumentoDAO {
	public List<TipoDocumento> getTiposDocumento(FamiliasDocumento familia);
}
