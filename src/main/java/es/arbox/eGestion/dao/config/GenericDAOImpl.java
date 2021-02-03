package es.arbox.eGestion.dao.config;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDAOImpl implements GenericDAO {
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
    public <T> void guardar(T objeto) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(objeto);
    }
	
	@Override
    public <T> void eliminar(Class<T> clase, int id) {
        Session session = sessionFactory.getCurrentSession();
        T objeto = session.byId(clase).load(id);
        session.delete(objeto);
    }

    @Override
    public <T> T obtenerPorId(Class<T> clase, int theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        T objeto = currentSession.get(clase, theId);
        return objeto;
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public <T> List<T> obtenerTodos(Class<T> clazz) {
    	Session session = sessionFactory.getCurrentSession();
    	return session.createQuery("from " + clazz.getName()).list();
    }
}
