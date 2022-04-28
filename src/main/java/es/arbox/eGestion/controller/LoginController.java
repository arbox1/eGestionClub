package es.arbox.eGestion.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
	    return "login";
	}
	
	@PostMapping("/logar2")
    public String logar2(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttrs) {
		
		String ret = "redirect:/login/";
		
		UserDetails userDetails = userDetailsService.loadUserByUsername (usuario.getUsername());
		
		if(userDetails == null) {
			Mensajes mensajes = new Mensajes();
			mensajes.mensaje(TiposMensaje.danger, String.format("Usuario y contraseña incorrecta"));
			redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		} else {
			Integer intento = ((Usuario)userDetails).getIntento() == null ? 0 : ((Usuario)userDetails).getIntento();
			
			if(userDetails != null && intento >= 3) {
				Mensajes mensajes = new Mensajes();
				mensajes.mensaje(TiposMensaje.danger, String.format("Usuario bloqueado contacte con el administrador"));
				redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
			} else if(userDetails != null && userDetails.getPassword().equals(Utilidades.getMd5(usuario.getPassword()))) {
				Authentication auth = new UsernamePasswordAuthenticationToken (userDetails,userDetails.getPassword (),userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				Mensajes mensajes = new Mensajes();
				mensajes.mensaje(TiposMensaje.success, String.format("Usuario logado correctamente."));
				redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
				
				((Usuario)userDetails).setIntento(0);
				menuService.guardar(((Usuario)userDetails));
				
				ret = "redirect:/principal/";
			} else {
				((Usuario)userDetails).setIntento(intento +1);
				Mensajes mensajes = new Mensajes();
				mensajes.mensaje(TiposMensaje.danger, String.format("Contraseña incorrecta. Intentos: %1$s de 3", ((Usuario)userDetails).getIntento()));
				redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
				
				menuService.guardar(((Usuario)userDetails));
			}
		}
		
		return ret;
    }
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Sesión cerrada correctamente."));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/login/";
	}
}
