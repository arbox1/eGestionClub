package es.arbox.eGestion.dao.economica;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.economica.DocumentoIngresoGasto;

@Repository
public class DocumentoIngresoGastoDAOImpl implements DocumentoIngresoGastoDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<DocumentoIngresoGasto> getDocumentos(Integer idIngresoGasto) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<DocumentoIngresoGasto> query = session.createNamedQuery("documentos_ingreso_gasto", DocumentoIngresoGasto.class);
        query.setParameter("idIngresoGasto", idIngresoGasto);
		return query.getResultList();
	}
}
