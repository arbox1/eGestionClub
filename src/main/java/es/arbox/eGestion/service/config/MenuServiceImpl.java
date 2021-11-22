package es.arbox.eGestion.service.config;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.config.MenuDAO;
import es.arbox.eGestion.entity.config.MenuEstructura;

@Service
public class MenuServiceImpl extends GenericServiceImpl implements MenuService {
	
	@Autowired
	private MenuDAO menuDAO;
	
	@Override
	@Transactional
	public List<MenuEstructura> getMenuEstructura(Integer idTipoMenu){
		return menuDAO.getMenuEstructura(idTipoMenu);
	}
	
	@Override
	@Transactional
	public List<MenuEstructura> getMenuEstructura(Integer idTipoMenu, Integer idUsuario){
		return menuDAO.getMenuEstructura(idTipoMenu, idUsuario);
	}
}