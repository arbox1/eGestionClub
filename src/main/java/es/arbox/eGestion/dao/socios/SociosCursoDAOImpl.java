package es.arbox.eGestion.dao.socios;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
//	@Override
//	public List<Meses> getMeses() {
//		Session session = sessionFactory.getCurrentSession();
//		TypedQuery<Meses> query = session.createNamedQuery("meses", Meses.class);
//		return query.getResultList();
//	}
}
