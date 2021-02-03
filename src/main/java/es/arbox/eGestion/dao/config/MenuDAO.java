package es.arbox.eGestion.dao.config;

import java.util.List;

import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuEstructura;

public interface MenuDAO {
	public List<MenuEstructura> getMenuEstructura(Integer idTipoMenu);
	
	public List<Menu> getMenuByIdEstructura(Integer idMenuEstructura);
}
