package es.arbox.eGestion.dao.socios;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.socios.DocumentoSocio;

@Repository
public class DocumentoSocioDAOImpl implements DocumentoSocioDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<DocumentoSocio> getDocumentos(Integer idSocio) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<DocumentoSocio> query = session.createNamedQuery("documentos_socio", DocumentoSocio.class);
        query.setParameter("idSocio", idSocio);
		return query.getResultList();
	}
	
	public DocumentoSocio getDocumentoFoto(Integer idSocio) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<DocumentoSocio> query = session.createNamedQuery("documento_foto", DocumentoSocio.class);
        query.setParameter("idSocio", idSocio);
        List<DocumentoSocio> lista = query.getResultList(); 
		return lista != null && lista.size() > 0 ? lista.get(0) : null;
	}
}
