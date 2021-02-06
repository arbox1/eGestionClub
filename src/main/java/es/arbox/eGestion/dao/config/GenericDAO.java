package es.arbox.eGestion.dao.config;

import java.util.List;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

public interface GenericDAO {
	public <T> void guardar(T tipo);

	public <T> void eliminar(Class<T> clase, int id);

	public <T> T obtenerPorId(Class<T> clase, int theId);
	
	public <T> List<T> obtenerTodosOrden(Class<T> clazz, String orders);
	
	public <T> List<T> obtenerFiltros(Class<T> clazz, List<Predicate> where, List<Order> order);
}
