package es.arbox.eGestion.dao.config;

import java.util.List;

import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.config.UsuarioRol;

public interface UsuarioRolDAO {
	
	public List<UsuarioRol> getUsuarioRol(Usuario usuario);
}
