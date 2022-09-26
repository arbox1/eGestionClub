package es.arbox.eGestion.dao.liga;

import java.util.List;

import es.arbox.eGestion.entity.ligas.Liga;

public interface LigaDAO {

	public List<Liga> getLigasFiltro(Liga liga);
}
