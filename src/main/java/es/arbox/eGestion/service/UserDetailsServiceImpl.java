package es.arbox.eGestion.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.UsuarioDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        return usuarioDAO.usuarioByUsername(username);
    }
    
}
