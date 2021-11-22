package es.arbox.eGestion.dao;

import es.arbox.eGestion.entity.config.Usuario;

public interface UsuarioDAO {

	public Usuario usuarioByUsername(String username);
}
