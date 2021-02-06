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
import org.springframework.util.StringUtils;

import es.arbox.eGestion.entity.economica.IngresosGastos;

@Repository
public class IngresosGastosDAOImpl implements IngresosGastosDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public List<IngresosGastos> getBusqueda(IngresosGastos ingresosGastos){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<IngresosGastos> q = cb.createQuery(IngresosGastos.class);
		Root<IngresosGastos> gastos = q.from(IngresosGastos.class);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(ingresosGastos.getSubTipo() != null && ingresosGastos.getSubTipo().getId() != null)
			predicados.add(cb.equal(gastos.get("subTipo").get("id"), ingresosGastos.getSubTipo().getId()));
		
		if(!StringUtils.isEmpty(ingresosGastos.getDescripcion()))
			predicados.add(cb.like(cb.lower(gastos.get("descripcion")), "%"+ingresosGastos.getDescripcion().toLowerCase()+"%"));
		
		if(ingresosGastos.getFechaDesde() != null) {
			predicados.add(cb.greaterThanOrEqualTo(gastos.get("fecha"), ingresosGastos.getFechaDesde()));
		}
		
		if(ingresosGastos.getFechaHasta() != null) {
			predicados.add(cb.lessThanOrEqualTo(gastos.get("fecha"), ingresosGastos.getFechaHasta()));
		}
		
		q.where(predicados.toArray(new Predicate[0])).orderBy(cb.desc(gastos.get("fecha")));
    	
		TypedQuery<IngresosGastos> query = session.createQuery(q);
    	return query.getResultList();
	}
}
