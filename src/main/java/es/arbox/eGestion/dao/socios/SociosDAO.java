package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Socios;

public interface SociosDAO {
	public List<Socios> getSocios();
	public List<Socios> getBusqueda(Socios socio);
}
