package es.arbox.eGestion.dao.socios;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.socios.Socios;

@Repository
public class SociosDAOImpl implements SociosDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<Socios> getSocios() {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<Socios> query = session.createNamedQuery("socios", Socios.class);
		return query.getResultList();
	}

}
