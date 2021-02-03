package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Cuota;

public interface CuotaDAO {
	public List<Cuota> getCuotas(Integer idSocioCurso);
}
