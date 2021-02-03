package es.arbox.eGestion.dao.config;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuEstructura;

@Repository
public class MenuDAOImpl implements MenuDAO{
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
    public List <MenuEstructura> getMenuEstructura(Integer idTipoMenu) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<MenuEstructura> query = session.createNamedQuery("menuEstructura.porTipo", MenuEstructura.class)
        											.setParameter("idTipoMenu", idTipoMenu);
        
        List <MenuEstructura> result = query.getResultList();
        for (MenuEstructura menuEstructura : result) {
        	menuEstructura.setMenus(getMenuByIdEstructura(menuEstructura.getId()));
        }
        return result;
    }
	
	public List<Menu> getMenuByIdEstructura(Integer idMenuEstructura){
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<Menu> query = session.createNamedQuery("menu.porMenuEstructura", Menu.class)
        											.setParameter("idMenuEstructura", idMenuEstructura);
        
        return query.getResultList();
	}
}