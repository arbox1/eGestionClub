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

import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Jornada;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;

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
	
	public List<Grupo> getGrupos(Liga liga) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Grupo> q = cb.createQuery(Grupo.class);
		Root<Grupo> grupos = q.from(Grupo.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(liga.getId() != null) {
			predicados.add(cb.equal(grupos.get("liga").get("id"), liga.getId()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.asc(grupos.get("id")));
    	
		TypedQuery<Grupo> query = session.createQuery(q);
    	return query.getResultList();
	}

	public List<Jornada> getJornadas(Grupo grupo) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Jornada> q = cb.createQuery(Jornada.class);
		Root<Jornada> jornadas = q.from(Jornada.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(grupo.getId() != null) {
			predicados.add(cb.equal(jornadas.get("grupo").get("id"), grupo.getId()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.asc(jornadas.get("numero")));
    	
		TypedQuery<Jornada> query = session.createQuery(q);
    	return query.getResultList();
	}

	public List<CalendarioLiga> getCalendarios(Jornada jornada) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<CalendarioLiga> q = cb.createQuery(CalendarioLiga.class);
		Root<CalendarioLiga> calendarios = q.from(CalendarioLiga.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(jornada.getId() != null) {
			predicados.add(cb.equal(calendarios.get("jornada").get("id"), jornada.getId()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.asc(calendarios.get("id")));
    	
		TypedQuery<CalendarioLiga> query = session.createQuery(q);
    	return query.getResultList();
	}
	
	public List<Resultado> getResultados(CalendarioLiga calendario) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Resultado> q = cb.createQuery(Resultado.class);
		Root<Resultado> resultados = q.from(Resultado.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(calendario.getId() != null) {
			predicados.add(cb.equal(resultados.get("calendario").get("id"), calendario.getId()));
		}
		
		if(calendario.getEquipoa() != null && calendario.getEquipoa().getId() != null) {
			predicados.add(
					cb.or(
							cb.equal(resultados.get("calendario").get("equipoa").get("id"), calendario.getEquipoa().getId()),
							cb.equal(resultados.get("calendario").get("equipob").get("id"), calendario.getEquipoa().getId())
						)
					);
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.asc(resultados.get("calendario").get("jornada").get("numero")));
    	
		TypedQuery<Resultado> query = session.createQuery(q);
    	return query.getResultList();
	}
}
