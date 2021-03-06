package es.arbox.eGestion.dao.config;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
	public <T> List<T> obtenerTodosOrden(Class<T> clazz, String orders) {
    	Session session = sessionFactory.getCurrentSession();
    	return session.createQuery(String.format("from %1$s %2$s", clazz.getName(), StringUtils.isEmpty(orders) ? "" : " order by " + orders)).list();
    }
	
	@Override
	public <T> List<T> obtenerFiltros(Class<T> clazz, List<Predicate> where, List<Order> order) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> q = cb.createQuery(clazz);
		Root<T> rootT = q.from(clazz);
		
		q.select(rootT).where(where.toArray(new Predicate[0]));
		
		if(order != null && order.size() > 0)
			q.orderBy(order);
    	
		TypedQuery<T> query = session.createQuery(q);
    	return query.getResultList();
    }
}
