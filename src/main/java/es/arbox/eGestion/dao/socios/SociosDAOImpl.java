package es.arbox.eGestion.dao.socios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import es.arbox.eGestion.entity.socios.Socios;
import es.arbox.eGestion.entity.socios.SociosCurso;

@Repository
public class SociosDAOImpl implements SociosDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<Socios> getSocios() {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<Socios> query = session.createNamedQuery("socios", Socios.class);
		return query.getResultList();
	}

	@Override
	public List<Socios> getBusqueda(Socios socio) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Socios> q = cb.createQuery(Socios.class);
		Root<Socios> socios = q.from(Socios.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		q.select(socios);
		if(!StringUtils.isEmpty(socio.getApellidos()))
			predicados.add(cb.like(cb.lower(socios.get("apellidos")), "%"+socio.getApellidos().toLowerCase()+"%"));
		
		if(!StringUtils.isEmpty(socio.getNombre()))
			predicados.add(cb.like(cb.lower(socios.get("nombre")), "%"+socio.getNombre().toLowerCase()+"%"));
		
		if(socio.getIdCurso() != null || socio.getIdCategoria() != null || socio.getIdEscuela() != null) {
			Subquery<SociosCurso> socioCurso = q.subquery(SociosCurso.class);
			Root<SociosCurso> sociosCurso = socioCurso.from(SociosCurso.class);
			List<Predicate> predicadosSq = new ArrayList<Predicate>();
			
			socioCurso.select(sociosCurso);
			predicadosSq.add(cb.equal(sociosCurso.get("socio").get("id"), socios.get("id")));
			
			if(socio.getIdCurso() != null)
				predicadosSq.add(cb.equal(sociosCurso.get("curso").get("id"), socio.getIdCurso()));
			
			if(socio.getIdCategoria() != null)
				predicadosSq.add(cb.equal(sociosCurso.get("categoria").get("id"), socio.getIdCategoria()));
			
			if(socio.getIdEscuela() != null)
				predicadosSq.add(cb.equal(sociosCurso.get("escuela").get("id"), socio.getIdEscuela()));
			
			socioCurso.where(predicadosSq.toArray(new Predicate[0]));
			predicados.add(cb.exists(socioCurso));
		}
		
		if(!StringUtils.isEmpty(socio.getDni()))
			predicados.add(cb.equal(cb.upper(socios.get("dni")), socio.getDni().toUpperCase()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.asc(socios.get("apellidos")),
															  cb.asc(socios.get("nombre")));
    	
		TypedQuery<Socios> query = session.createQuery(q);
    	return query.getResultList();
	}
}
