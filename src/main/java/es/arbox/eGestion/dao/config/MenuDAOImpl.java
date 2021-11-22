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
        	menuEstructura.setMenus(getMenuByIdEstructura(menuEstructura.getId(), null));
        }
        return result;
    }
	
	@Override
    public List <MenuEstructura> getMenuEstructura(Integer idTipoMenu, Integer idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<MenuEstructura> query = session.createNamedQuery("menuEstructura.porTipo", MenuEstructura.class)
        											.setParameter("idTipoMenu", idTipoMenu);
        
        List <MenuEstructura> result = query.getResultList();
        for (MenuEstructura menuEstructura : result) {
        	menuEstructura.setMenus(getMenuByIdEstructura(menuEstructura.getId(), idUsuario));
        }
        return result;
    }
	
	public List<Menu> getMenuByIdEstructura(Integer idMenuEstructura, Integer idUsuario){
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<Menu> query = session.createNamedQuery(idUsuario != null ? "menu.porMenuEstructura" : "menu.porMenuEstructuraSinUsuario", Menu.class)
        											.setParameter("idMenuEstructura", idMenuEstructura)
        											.setParameter("idUsuario", idUsuario);
        return query.getResultList();
        
//        Session session = sessionFactory.getCurrentSession();
//		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
//		
//		CriteriaQuery<MenuRol> q = cb.createQuery(MenuRol.class);
//		Root<MenuRol> menusRoles = q.from(MenuRol.class);
//		List<Predicate> predicados = new ArrayList<Predicate>();
//		
//		predicados.add(cb.equal(menusRoles.get("rol").get("id"), idRol));
//		predicados.add(cb.equal(menusRoles.get("menu").get("menuEstructura").get("id"), idMenuEstructura));
//		
//		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(menusRoles.get("menu").get("orden")));
//		
//		TypedQuery<MenuRol> query = session.createQuery(q);
//		
//		List<Menu> lMenu = new ArrayList<>();
//		for(MenuRol mrol : query.getResultList()) {
//			lMenu.add(mrol.getMenu());
//		}
//        
//        return lMenu;
	}
}