package es.arbox.eGestion.dao.economica;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.economica.SubtiposImporte;

@Repository
public class SubtiposImporteDAOImpl implements SubtiposImporteDAO{
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<SubtiposImporte> getSubtiposImporte(Integer idTipo){
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<SubtiposImporte> query = session.createNamedQuery("subtiposImporte", SubtiposImporte.class);
        query.setParameter("idTipo", idTipo);
		return query.getResultList(); 
	}
}