package es.arbox.eGestion.service.config;

import java.util.List;

import es.arbox.eGestion.entity.config.MenuEstructura;

public interface MenuService extends GenericService {
	public List<MenuEstructura> getMenuEstructura(Integer idTipoMenu);
	public List<MenuEstructura> getMenuEstructura(Integer idTipoMenu, Integer idUsuario);
}
