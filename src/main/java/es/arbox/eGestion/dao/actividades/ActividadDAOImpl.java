package es.arbox.eGestion.dao.actividades;

import java.util.ArrayList;
import java.util.Date;
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

import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.Participante;

@Repository
public class ActividadDAOImpl implements ActividadDAO {

	@Autowired
    private SessionFactory sessionFactory;

	public List<Actividad> getActividadesFiltro(Actividad actividad) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Actividad> q = cb.createQuery(Actividad.class);
		Root<Actividad> actividades = q.from(Actividad.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(!StringUtils.isEmpty(actividad.getDescripcion()))
			predicados.add(cb.equal(actividades.get("descripcion"), actividad.getDescripcion()));
		
		if(!StringUtils.isEmpty(actividad.getFechaFinPlazo())) {
			predicados.add(cb.greaterThanOrEqualTo(actividades.get("fechaFinPlazo").as(Date.class), actividad.getFechaFinPlazo()));
		}
		
		if(actividad.getTipo() != null && actividad.getTipo().getId() != null) {
			predicados.add(cb.equal(actividades.get("tipo").get("id"), actividad.getTipo().getId()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(actividades.get("fechaInicio")));
    	
		TypedQuery<Actividad> query = session.createQuery(q);
    	return query.getResultList();
	}

	public List<Participante> getParticipantes(Integer idActividad) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<Participante> q = cb.createQuery(Participante.class);
		Root<Participante> participantes = q.from(Participante.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(participantes.get("actividad").get("id"), idActividad));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(participantes.get("fecha")));
		
		TypedQuery<Participante> query = session.createQuery(q);
    	return query.getResultList();
	}
	
	public Integer getInscritos(Integer idActividad) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
		Root<Participante> root = query.from(Participante.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		query.select(cb.sum(root.<Integer> get("cantidad")));
		
		predicados.add(cb.equal(root.get("actividad").get("id"), idActividad));
		query.where(predicados.toArray(new Predicate[0]));
		
		return (Integer) session.createQuery(query).getSingleResult();
	}
}
