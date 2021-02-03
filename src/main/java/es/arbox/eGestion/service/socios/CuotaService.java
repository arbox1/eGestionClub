package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.service.config.GenericService;

public interface CuotaService extends GenericService {
	public List<Cuota> getCuotas(Integer idSocioCurso);
}
