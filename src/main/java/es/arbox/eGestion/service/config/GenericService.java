package es.arbox.eGestion.service.config;

import java.util.List;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.entity.config.Usuario;

public interface GenericService {
	
	public <T> void guardar(T objeto, Usuario usuario) throws IllegalArgumentException, IllegalAccessException;
	
	public <T> void guardar(T objeto);

	public <T> void eliminar(Class<T> clase, int id);

	public <T> T obtenerPorId(Class<T> clase, int theId);
	
	public <T> List<T> obtenerTodos(Class<T> clazz);
	
	public <T> List<T> obtenerTodosOrden(Class<T> clazz, String orden);
	
	public String serializa(Object o) throws JsonProcessingException;

	public <T> List<T> obtenerFiltros(Class<T> clazz, List<Predicate> where, List<Order> order);
}
