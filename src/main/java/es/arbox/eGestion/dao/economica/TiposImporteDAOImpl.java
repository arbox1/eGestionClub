package es.arbox.eGestion.dao.economica;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.economica.TiposImporte;

@Repository
public class TiposImporteDAOImpl implements TiposImporteDAO{
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<TiposImporte> getTiposImporte(){
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<TiposImporte> query = session.createNamedQuery("tiposImporte", TiposImporte.class);
		return query.getResultList(); 
	}
}