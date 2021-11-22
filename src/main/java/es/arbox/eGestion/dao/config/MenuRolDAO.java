package es.arbox.eGestion.dao.config;

import java.util.List;

import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuRol;

public interface MenuRolDAO {
	
	public List<MenuRol> getMenuRol(Menu menu);
}
