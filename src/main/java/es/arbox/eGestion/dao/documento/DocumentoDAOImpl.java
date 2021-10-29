package es.arbox.eGestion.dao.documento;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.FamiliasDocumento;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	public List<TipoDocumento> getTiposDocumento(FamiliasDocumento familia) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<TipoDocumento> query = session.createNamedQuery("tipo_documento_familia", TipoDocumento.class);
        query.setParameter("familia", familia.toString());
		return query.getResultList();
	}
}
