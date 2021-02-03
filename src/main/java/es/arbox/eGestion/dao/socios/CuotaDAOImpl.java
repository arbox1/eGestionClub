package es.arbox.eGestion.dao.socios;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.arbox.eGestion.entity.socios.Cuota;

@Repository
public class CuotaDAOImpl implements CuotaDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public List<Cuota> getCuotas(Integer idSocioCurso) {
		Session session = sessionFactory.getCurrentSession();
        TypedQuery<Cuota> query = session.createNamedQuery("cuotas", Cuota.class);
        query.setParameter("idSocioCurso", idSocioCurso);
		return query.getResultList();
	}
}
