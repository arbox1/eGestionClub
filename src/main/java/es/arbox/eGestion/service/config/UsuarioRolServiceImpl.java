package es.arbox.eGestion.service.config;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.config.UsuarioRolDAO;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.config.UsuarioRol;

@Service
public class UsuarioRolServiceImpl extends GenericServiceImpl implements UsuarioRolService {
	
	@Autowired
	private UsuarioRolDAO usuarioRolDAO;
	
	@Override
	@Transactional
	public List<UsuarioRol> getUsuarioRol(Usuario usuario) {
		return usuarioRolDAO.getUsuarioRol(usuario);
	}
}
