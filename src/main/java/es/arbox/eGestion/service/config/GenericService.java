package es.arbox.eGestion.service.config;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GenericService {
	
	public <T> void guardar(T objeto);

	public <T> void eliminar(Class<T> clase, int id);

	public <T> T obtenerPorId(Class<T> clase, int theId);
	
	public <T> List<T> obtenerTodos(Class<T> clazz);
	
	public String serializa(Object o) throws JsonProcessingException;
}
