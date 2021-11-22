package es.arbox.eGestion.service.config;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.config.MenuRolDAO;
import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuRol;

@Service
public class MenuRolServiceImpl extends GenericServiceImpl implements MenuRolService {
	
	@Autowired
	private MenuRolDAO menuRolDAO;
	
	@Override
	@Transactional
	public List<MenuRol> getMenuRol(Menu menu) {
		return menuRolDAO.getMenuRol(menu);
	}
}
