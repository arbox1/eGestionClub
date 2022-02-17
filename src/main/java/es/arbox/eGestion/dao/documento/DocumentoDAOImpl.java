package es.arbox.eGestion.dao.documento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	public List<Documento> getBusqueda(Documento documento){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Documento> q = cb.createQuery(Documento.class);
		Root<Documento> documentos = q.from(Documento.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(documento.getTipo() != null && documento.getTipo().getId() != null)
			predicados.add(cb.equal(documentos.get("tipo").get("id"), documento.getTipo().getId()));
		
		if(StringUtils.isNotBlank(documento.getDescripcion())) {
			predicados.add(cb.equal(documentos.get("descripcion"), documento.getDescripcion()));
		}
		
		predicados.add(cb.equal(documentos.get("tipo").get("familia").get("descripcion"), FamiliasDocumento.DOCUMENTACION.toString()));
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(documentos.get("descripcion")));
    	
		TypedQuery<Documento> query = session.createQuery(q);
    	return query.getResultList();
	}

	public List<TipoDocumento> getTiposDocumento(FamiliasDocumento familia) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<TipoDocumento> query = session.createNamedQuery("tipo_documento_familia", TipoDocumento.class);
        query.setParameter("familia", familia.toString());
		return query.getResultList();
	}
}
