package es.arbox.eGestion.dao.liga;

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
import org.springframework.util.StringUtils;

import es.arbox.eGestion.entity.ligas.Liga;

@Repository
public class LigaDAOImpl implements LigaDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<Liga> getLigasFiltro(Liga liga) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Liga> q = cb.createQuery(Liga.class);
		Root<Liga> ligas = q.from(Liga.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(liga.getId() != null) {
			predicados.add(cb.equal(ligas.get("id"), liga.getId()));
		}
		
		if(!StringUtils.isEmpty(liga.getDescripcion()))
			predicados.add(cb.like(cb.upper(ligas.get("descripcion")), "%"+liga.getDescripcion().toUpperCase()+"%"));
		
		if(liga.getTipo() != null && liga.getTipo().getId() != null) {
			predicados.add(cb.equal(ligas.get("tipo").get("id"), liga.getTipo().getId()));
		}
		
		if(liga.getEstado() != null && liga.getEstado().getId() != null) {
			predicados.add(cb.equal(ligas.get("estado").get("id"), liga.getEstado().getId()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(ligas.get("id")));
    	
		TypedQuery<Liga> query = session.createQuery(q);
    	return query.getResultList();
	}

}
