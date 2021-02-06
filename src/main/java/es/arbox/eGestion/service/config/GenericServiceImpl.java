package es.arbox.eGestion.service.config;

import java.util.List;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.arbox.eGestion.dao.config.GenericDAO;

@Service
public class GenericServiceImpl implements GenericService{
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Override
	@Transactional
	public <T> void guardar(T objeto) {
		genericDAO.guardar(objeto);
	}

	@Override
	@Transactional
	public <T> void eliminar(Class<T> clase, int id) {
		genericDAO.eliminar(clase, id);
	}

	@Override
	@Transactional
	public <T> T obtenerPorId(Class<T> clase, int theId) {
		return genericDAO.obtenerPorId(clase, theId);
	}
	
	@Override
	@Transactional
	public <T> List<T> obtenerTodos(Class<T> clazz) {
		return genericDAO.obtenerTodosOrden(clazz, null);
	}
	
	@Override
	@Transactional
	public <T> List<T> obtenerTodosOrden(Class<T> clazz, String orden) {
		return genericDAO.obtenerTodosOrden(clazz, orden);
	}
	
	@Override
	@Transactional
	public <T> List<T> obtenerFiltros(Class<T> clazz, List<Predicate> where, List<Order> order){
		return genericDAO.obtenerFiltros(clazz, where, order);
	}
	
	public String serializa(Object o) throws JsonProcessingException {
		ObjectMapper Obj = new ObjectMapper();
		Obj.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return Obj.writeValueAsString(o);
	}
}
