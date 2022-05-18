package es.arbox.eGestion.dao.actividades;

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

import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.enums.TiposDocumento;

@Repository
public class DocumentoActividadDAOImpl implements DocumentoActividadDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<DocumentoActividad> getDocumentos(Integer idActividad) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<DocumentoActividad> query = session.createNamedQuery("documentos_actividad", DocumentoActividad.class);
        query.setParameter("idActividad", idActividad);
		return query.getResultList();
	}
	
	public List<DocumentoActividad> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<DocumentoActividad> q = cb.createQuery(DocumentoActividad.class);
		Root<DocumentoActividad> documentoActividad = q.from(DocumentoActividad.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(documentoActividad.get("actividad").get("id"), idActividad));
		
		predicados.add(cb.equal(documentoActividad.get("documento").get("tipo").get("codigo"), tipoDocumento.toString()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(documentoActividad.get("id")));
    	
		TypedQuery<DocumentoActividad> query = session.createQuery(q);
    	return query.getResultList();
	}
	
	public DocumentoActividad getDocumentoActividad(Integer idDocumento) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<DocumentoActividad> q = cb.createQuery(DocumentoActividad.class);
		Root<DocumentoActividad> documentoActividad = q.from(DocumentoActividad.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(documentoActividad.get("documento").get("id"), idDocumento));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(documentoActividad.get("id")));
    	
		TypedQuery<DocumentoActividad> query = session.createQuery(q);
    	return query.getSingleResult();
	}
}
