package es.arbox.eGestion.dao.economica;

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

import es.arbox.eGestion.entity.economica.Pago;

@Repository
public class PagosDAOImpl implements PagosDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<Pago> getBusqueda(Pago pago){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Pago> q = cb.createQuery(Pago.class);
		Root<Pago> pagos = q.from(Pago.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(pago.getUsuario() != null && pago.getUsuario().getId() != null)
			predicados.add(cb.equal(pagos.get("usuario").get("id"), pago.getUsuario().getId()));
		
		if(pago.getMes() != null) {
			predicados.add(cb.equal(cb.function("month", Integer.class, pagos.get("fecha")), pago.getMes()));
		}
//		
//		if(ingresosGastos.getFechaDesde() != null) {
//			predicados.add(cb.greaterThanOrEqualTo(gastos.get("fecha"), ingresosGastos.getFechaDesde()));
//		}
//		
//		if(ingresosGastos.getFechaHasta() != null) {
//			predicados.add(cb.lessThanOrEqualTo(gastos.get("fecha"), ingresosGastos.getFechaHasta()));
//		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(pagos.get("fecha")));
    	
		TypedQuery<Pago> query = session.createQuery(q);
    	return query.getResultList();
	}
}
