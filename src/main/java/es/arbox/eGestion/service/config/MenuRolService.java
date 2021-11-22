package es.arbox.eGestion.service.config;

import java.util.List;

import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuRol;

public interface MenuRolService extends GenericService {
	public List<MenuRol> getMenuRol(Menu menu);
}
