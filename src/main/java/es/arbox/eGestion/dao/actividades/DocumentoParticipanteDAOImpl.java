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

import es.arbox.eGestion.entity.actividades.DocumentoParticipante;
import es.arbox.eGestion.enums.TiposDocumento;

@Repository
public class DocumentoParticipanteDAOImpl implements DocumentoParticipanteDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<DocumentoParticipante> getDocumentos(Integer idActividad) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<DocumentoParticipante> query = session.createNamedQuery("documentos_participante", DocumentoParticipante.class);
        query.setParameter("idParticipante", idActividad);
		return query.getResultList();
	}
	
	public List<DocumentoParticipante> getDocumentosPorTipo(Integer idActividad, TiposDocumento tipoDocumento) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<DocumentoParticipante> q = cb.createQuery(DocumentoParticipante.class);
		Root<DocumentoParticipante> documentoParticipante = q.from(DocumentoParticipante.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(documentoParticipante.get("actividad").get("id"), idActividad));
		
		predicados.add(cb.equal(documentoParticipante.get("documento").get("tipo").get("codigo"), tipoDocumento.toString()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(documentoParticipante.get("id")));
    	
		TypedQuery<DocumentoParticipante> query = session.createQuery(q);
    	return query.getResultList();
	}
	
	public DocumentoParticipante getDocumentoParticipante(Integer idDocumento) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<DocumentoParticipante> q = cb.createQuery(DocumentoParticipante.class);
		Root<DocumentoParticipante> documentoParticipante = q.from(DocumentoParticipante.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		predicados.add(cb.equal(documentoParticipante.get("documento").get("id"), idDocumento));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(documentoParticipante.get("id")));
    	
		TypedQuery<DocumentoParticipante> query = session.createQuery(q);
    	return query.getSingleResult();
	}
}
