package es.arbox.eGestion.dao.socios;

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

import es.arbox.eGestion.entity.socios.AsistenciaMonitor;

@Repository
public class AsistenciaMonitorDAOImpl implements AsistenciaMonitorDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<AsistenciaMonitor> getAsistencias(AsistenciaMonitor asistencia) {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AsistenciaMonitor> q = cb.createQuery(AsistenciaMonitor.class);
		Root<AsistenciaMonitor> asistencias = q.from(AsistenciaMonitor.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(asistencia.getId() != null) {
			predicados.add(cb.equal(asistencias.get("id"), asistencia.getId()));
		}
		
		if(asistencia.getMonitor() != null && asistencia.getMonitor().getId() != null) {
			predicados.add(cb.equal(asistencias.get("monitor").get("id"), asistencia.getMonitor().getId()));
		}
		
		if(asistencia.getTarifa() != null && asistencia.getTarifa().getId() != null) {
			predicados.add(cb.equal(asistencias.get("tarifa").get("id"), asistencia.getTarifa().getId()));
		}
		
		if(asistencia.getEscuela() != null && asistencia.getEscuela().getId() != null) {
			predicados.add(cb.equal(asistencias.get("escuela").get("id"), asistencia.getEscuela().getId()));
		}
		
		if(!StringUtils.isEmpty(asistencia.getFechaDesde())) {
			predicados.add(cb.greaterThanOrEqualTo(asistencias.get("fecha").as(Date.class), asistencia.getFechaDesde()));
		}
		
		if(!StringUtils.isEmpty(asistencia.getFechaHasta())) {
			predicados.add(cb.lessThanOrEqualTo(asistencias.get("fecha").as(Date.class), asistencia.getFechaHasta()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(asistencias.get("fecha")));
    	
		TypedQuery<AsistenciaMonitor> query = session.createQuery(q);
    	return query.getResultList();
	}
}
