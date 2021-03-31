package es.arbox.eGestion.service.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.service.config.GenericService;

public interface SociosCursoService extends GenericService {
	public List<SociosCurso> getSociosCurso(Integer idSocio);
	public List<SociosCurso> obtenerSociosFiltro(Integer idCurso, Integer idEscuela, Integer idCategoria);
//	public List<Curso> getCursos();
//	public List<Escuela> getEscuelas();
//	public List<Categoria> getCategorias();
	public List<Meses> getMeses();
}
