package es.arbox.eGestion.dao.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuRol;

@Repository
public class MenuRolDAOImpl implements MenuRolDAO {
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<MenuRol> getMenuRol(Menu menu) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<MenuRol> q = cb.createQuery(MenuRol.class);
		Root<MenuRol> menuRol = q.from(MenuRol.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(menuRol.get("menu").get("id"), menu.getId()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(menuRol.get("rol").get("descripcion")));
		
		TypedQuery<MenuRol> query = session.createQuery(q);
    	return query.getResultList();
	}

}
