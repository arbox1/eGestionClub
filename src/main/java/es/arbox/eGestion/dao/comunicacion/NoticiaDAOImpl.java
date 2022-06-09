package es.arbox.eGestion.dao.comunicacion;

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

import es.arbox.eGestion.entity.comunicacion.Noticia;

@Repository
public class NoticiaDAOImpl implements NoticiaDAO {
	@Autowired
    private SessionFactory sessionFactory;

	public List<Noticia> getNoticiasFiltro(Noticia noticia) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Noticia> q = cb.createQuery(Noticia.class);
		Root<Noticia> noticias = q.from(Noticia.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(noticia.getId() != null) {
			predicados.add(cb.equal(noticias.get("id"), noticia.getId()));
		}
		
		if(!StringUtils.isEmpty(noticia.getAsunto()))
			predicados.add(cb.like(noticias.get("asunto"), noticia.getAsunto()));
		
		if(!StringUtils.isEmpty(noticia.getDescripcion()))
			predicados.add(cb.like(noticias.get("descripcion"), noticia.getDescripcion()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(noticias.get("fecha")));
    	
		TypedQuery<Noticia> query = session.createQuery(q);
    	return query.getResultList();
	}
}
