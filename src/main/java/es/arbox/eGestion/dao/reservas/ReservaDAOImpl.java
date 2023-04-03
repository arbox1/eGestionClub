package es.arbox.eGestion.dao.reservas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Service;

import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;

@Service
public class ReservaDAOImpl implements ReservaDAO  {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<HorarioPista> getHorarios(HorarioPista h) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<HorarioPista> q = cb.createQuery(HorarioPista.class);
		Root<HorarioPista> horario = q.from(HorarioPista.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(horario.get("pista").get("id"), h.getPista().getId()));
		
		if(h.getDia() != null) {
			predicados.add(cb.equal(horario.get("dia"), h.getDia()));
		}
		
		if(h.getHora() != null) {
			predicados.add(cb.equal(horario.get("hora"), h.getHora()));
		}
		
		if(h.getMinuto() != null) {
			predicados.add(cb.equal(horario.get("minuto"), h.getMinuto()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(horario.get("dia")), cb.asc(horario.get("hora")), cb.asc(horario.get("minuto")));
		
		TypedQuery<HorarioPista> query = session.createQuery(q);
    	return query.getResultList();
	}
	
	public List<Reserva> getReservas(Reserva reserva) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<Reserva> q = cb.createQuery(Reserva.class);
		Root<Reserva> reservas = q.from(Reserva.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(reserva.getPista() != null && reserva.getPista().getId() != null) {
			predicados.add(cb.equal(reservas.get("pista").get("id"), reserva.getPista().getId()));
		}
		
		if(reserva.getFecha() != null) {
			predicados.add(cb.equal(cb.function("TO_CHAR", String.class,reservas.get("fecha"), cb.literal("DD/MM/YYYY")), format.format(reserva.getFecha())));
		}
		
		if(reserva.getFechaDesde() != null) {
			predicados.add(cb.greaterThanOrEqualTo(reservas.get("fecha").as(Date.class), reserva.getFechaDesde()));
		}
		
		if(reserva.getFechaHasta() != null) {
			predicados.add(cb.lessThanOrEqualTo(reservas.get("fecha").as(Date.class), reserva.getFechaHasta()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(reservas.get("pista").get("id")), cb.asc(reservas.get("fecha")));
		
		TypedQuery<Reserva> query = session.createQuery(q);
		return query.getResultList();
	}

}
