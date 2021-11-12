package es.arbox.eGestion.dao.socios;

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

import es.arbox.eGestion.entity.socios.Calendario;

@Repository
public class CalendarioDAOImpl implements CalendarioDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<Calendario> getCalendarios(Calendario calendario) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Calendario> q = cb.createQuery(Calendario.class);
		Root<Calendario> calendarios = q.from(Calendario.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(calendario.getCurso() != null && calendario.getCurso().getId() != null)
			predicados.add(cb.equal(calendarios.get("curso").get("id"), calendario.getCurso().getId()));
		
		if(calendario.getEscuela() != null && calendario.getEscuela().getId() != null)
			predicados.add(cb.equal(calendarios.get("escuela").get("id"), calendario.getEscuela().getId()));
		
		if(calendario.getCategoria() != null && calendario.getCategoria().getId() != null)
			predicados.add(cb.equal(calendarios.get("categoria").get("id"), calendario.getCategoria().getId()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(calendarios.get("fecha")));
    	
		TypedQuery<Calendario> query = session.createQuery(q);
    	return query.getResultList();
	}

}
