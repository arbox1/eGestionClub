package es.arbox.eGestion.dao.config;

import java.util.List;

public interface GenericDAO {
	public <T> void guardar(T tipo);

	public <T> void eliminar(Class<T> clase, int id);

	public <T> T obtenerPorId(Class<T> clase, int theId);
	
	public <T> List<T> obtenerTodos(Class<T> clazz);
}
