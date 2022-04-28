package es.arbox.eGestion.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.dao.config.UsuarioRolDAO;
import es.arbox.eGestion.entity.config.Rol;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.config.UsuarioRol;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private UsuarioRolDAO usuarioRolDAO;

	@Override
	public Usuario usuarioByUsername(String username) {
		
		Session session = sessionFactory.getCurrentSession();
//      TypedQuery<Usuario> query = session.createNamedQuery("usuarioPorUsername", Usuario.class)
//      											.setParameter("username", username);
//      
//      Usuario result = query.getSingleResult();
      
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Usuario> q = cb.createQuery(Usuario.class);
		Root<Usuario> usuarios = q.from(Usuario.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(usuarios.get("username"), username));
		predicados.add(
				cb.or(cb.isNull(usuarios.get("fechaBaja").as(Date.class)),
						cb.greaterThanOrEqualTo(usuarios.get("fechaBaja").as(Date.class), new Date()))
				
				);
		
//		predicados.add(
//				cb.or(cb.isNull(usuarios.get("intento").as(Integer.class)),
//						cb.lessThan(usuarios.get("intento").as(Integer.class), Integer.valueOf(3)))
//				);
		
		q.where(predicados.toArray(new Predicate[0]));

		TypedQuery<Usuario> query = session.createQuery(q);
		List<Usuario> lUsuarios = query.getResultList();
		
		if (lUsuarios == null || lUsuarios.size() == 0){
			return null;
		}
		Usuario result = lUsuarios.get(0);

		List<UsuarioRol> lRoles = usuarioRolDAO.getUsuarioRol(result);

		Set<Rol> sRoles = new HashSet<>();
		for (UsuarioRol usuarioRol : lRoles) {
			sRoles.add(usuarioRol.getRol());
		}

		result.setRoles(sRoles);

		return result;
	}

}
