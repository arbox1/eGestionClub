package es.arbox.eGestion.dao.economica;

import java.util.List;

import es.arbox.eGestion.entity.economica.DocumentoIngresoGasto;

public interface DocumentoIngresoGastoDAO {

	public List<DocumentoIngresoGasto> getDocumentos(Integer idIngresoGasto);
}
