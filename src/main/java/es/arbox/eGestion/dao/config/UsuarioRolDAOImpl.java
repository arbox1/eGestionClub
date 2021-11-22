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

import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.config.UsuarioRol;

@Repository
public class UsuarioRolDAOImpl implements UsuarioRolDAO {
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<UsuarioRol> getUsuarioRol(Usuario usuario) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<UsuarioRol> q = cb.createQuery(UsuarioRol.class);
		Root<UsuarioRol> usuarioRol = q.from(UsuarioRol.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(usuarioRol.get("usuario").get("id"), usuario.getId()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(usuarioRol.get("rol").get("descripcion")));
		
		TypedQuery<UsuarioRol> query = session.createQuery(q);
    	return query.getResultList();
	}

}
