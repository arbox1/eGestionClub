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

import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.SociosCurso;

@Repository
public class SociosCursoDAOImpl implements SociosCursoDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<SociosCurso> getSociosCurso(Integer idSocio) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<SociosCurso> query = session.createNamedQuery("socios_curso", SociosCurso.class);
        query.setParameter("idSocio", idSocio);
		return query.getResultList();
	}
	
	public List<SociosCurso> obtenerSociosFiltro(Integer idSocio, Integer idCurso, Integer idEscuela, Integer idCategoria) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<SociosCurso> query = session.createNamedQuery("socios_filtro", SociosCurso.class);
        query.setParameter("idSocio", idSocio != null ? idSocio : -1);
        query.setParameter("idCurso", idCurso != null ? idCurso : -1);
        query.setParameter("idEscuela", idEscuela != null ? idEscuela : -1);
        query.setParameter("idCategoria", idCategoria != null ? idCategoria : -1);
		return query.getResultList();
	}
	
	public SociosCurso getSocioCursoPorDni(String dni) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		
		CriteriaQuery<SociosCurso> q = cb.createQuery(SociosCurso.class);
		Root<SociosCurso> sociosCursos = q.from(SociosCurso.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(cb.upper(sociosCursos.get("socio").get("dni")), dni.toUpperCase()));
		
		q.where(predicados.toArray(new Predicate[0]));
		
		TypedQuery<SociosCurso> query = session.createQuery(q);
		
		List<SociosCurso> lista = query.getResultList();
		
		if(lista == null || lista.size() == 0) {
			return null;
		} else {
			return lista.get(0);
		}
	}
	
//	@Override
//	public List<Curso> getCursos() {
//		Session session = sessionFactory.getCurrentSession();
//		TypedQuery<Curso> query = session.createNamedQuery("cursos", Curso.class);
//		return query.getResultList();
//	}
//
//	@Override
//	public List<Escuela> getEscuelas() {
//		Session session = sessionFactory.getCurrentSession();
//		TypedQuery<Escuela> query = session.createNamedQuery("escuelas", Escuela.class);
//		return query.getResultList();
//	}
//	
//	@Override
//	public List<Categoria> getCategorias() {
//		Session session = sessionFactory.getCurrentSession();
//		TypedQuery<Categoria> query = session.createNamedQuery("categorias", Categoria.class);
//		return query.getResultList();
//	}
//	
	@Override
	public List<Meses> getMeses() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<Meses> query = session.createNamedQuery("meses", Meses.class);
		return query.getResultList();
	}
}
