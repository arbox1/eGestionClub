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

import es.arbox.eGestion.entity.reservas.BloqueoReserva;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.entity.reservas.UsuarioReserva;

@Service
public class ReservaDAOImpl implements ReservaDAO  {

	@Autowired
    private SessionFactory sessionFactory;
	
	public UsuarioReserva getUsuarioReserva(String email) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<UsuarioReserva> q = cb.createQuery(UsuarioReserva.class);
		Root<UsuarioReserva> usuarioReserva = q.from(UsuarioReserva.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(email != null) {
			predicados.add(cb.equal(usuarioReserva.get("email"), email));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(usuarioReserva.get("id")));
		
		TypedQuery<UsuarioReserva> query = session.createQuery(q);
		
		List<UsuarioReserva> lista = query.getResultList();
		
    	return lista != null && lista.size() > 0 ? lista.get(0) : new UsuarioReserva();
	}
	
	public boolean fechaBloqueada(HorarioPista h) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<BloqueoReserva> q = cb.createQuery(BloqueoReserva.class);
		Root<BloqueoReserva> bloqueo = q.from(BloqueoReserva.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(bloqueo.get("pista").get("id"), h.getPista().getId()));
		
		if(h.getFechaDesde() != null) {
			predicados.add(cb.greaterThanOrEqualTo(bloqueo.<Date>get("fechaHasta").as(Date.class), h.getFechaDesde()));
		}
		
		if(h.getFechaDesde() != null) {
			predicados.add(cb.lessThanOrEqualTo(bloqueo.<Date>get("fechaDesde").as(Date.class), h.getFechaDesde()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(bloqueo.get("id")));
		
		TypedQuery<BloqueoReserva> query = session.createQuery(q);
		
		return query.getResultList().size() > 0 ? true : false;
	}
	
	public List<BloqueoReserva> getBloqueos(BloqueoReserva br) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<BloqueoReserva> q = cb.createQuery(BloqueoReserva.class);
		Root<BloqueoReserva> bloqueo = q.from(BloqueoReserva.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(br.getPista() != null && br.getPista().getId() != null) {
			predicados.add(cb.equal(bloqueo.get("pista").get("id"), br.getPista().getId()));
		}
		
		if(br.getFechaDesde() != null) {
			predicados.add(cb.greaterThanOrEqualTo(bloqueo.<Date>get("fechaHasta").as(Date.class), br.getFechaDesde()));
		}
		
		if(br.getFechaHasta() != null) {
			predicados.add(cb.lessThanOrEqualTo(bloqueo.<Date>get("fechaDesde").as(Date.class), br.getFechaHasta()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(bloqueo.get("id")));
		
		TypedQuery<BloqueoReserva> query = session.createQuery(q);
    	return query.getResultList();
	}
	
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
			predicados.add(cb.equal(cb.function("DATE_FORMAT", String.class,reservas.get("fecha"), cb.literal("%d/%m/%Y")), format.format(reserva.getFecha())));
		}
		
		if(reserva.getFechaDesde() != null) {
			predicados.add(cb.greaterThanOrEqualTo(reservas.get("fecha").as(Date.class), reserva.getFechaDesde()));
		}
		
		if(reserva.getFechaHasta() != null) {
			predicados.add(cb.lessThanOrEqualTo(reservas.get("fecha").as(Date.class), reserva.getFechaHasta()));
		}
		
		if(reserva.getHash() != null) {
			predicados.add(cb.equal(reservas.get("hash"), reserva.getHash()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(reservas.get("pista").get("id")), cb.asc(reservas.get("fecha")));
		
		TypedQuery<Reserva> query = session.createQuery(q);
		return query.getResultList();
	}

}
