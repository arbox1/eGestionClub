package es.arbox.eGestion.service.config;

import java.util.List;

import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.config.UsuarioRol;

public interface UsuarioRolService extends GenericService {
	public List<UsuarioRol> getUsuarioRol(Usuario usuario);
}
