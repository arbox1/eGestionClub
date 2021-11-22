package es.arbox.eGestion.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

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
        TypedQuery<Usuario> query = session.createNamedQuery("usuarioPorUsername", Usuario.class)
        											.setParameter("username", username);
        
        Usuario result = query.getSingleResult();
        
        List<UsuarioRol> lRoles = usuarioRolDAO.getUsuarioRol(result);
        
        Set<Rol> sRoles = new HashSet<>();
        for(UsuarioRol usuarioRol : lRoles) {
        	sRoles.add(usuarioRol.getRol());
        }
        
        result.setRoles(sRoles);
        
        return result;
	}

}
