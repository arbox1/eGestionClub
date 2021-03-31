package es.arbox.eGestion.dao.socios;

import java.util.List;

import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.SociosCurso;

public interface SociosCursoDAO {
	public List<SociosCurso> getSociosCurso(Integer idSocio);
	public List<SociosCurso> obtenerSociosFiltro(Integer idCurso, Integer idEscuela, Integer idCategoria);
//	public List<Curso> getCursos();
//	public List<Escuela> getEscuelas();
//	public List<Categoria> getCategorias();
	public List<Meses> getMeses();
}
