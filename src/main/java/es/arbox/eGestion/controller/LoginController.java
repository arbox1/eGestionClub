package es.arbox.eGestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.arbox.eGestion.dto.Usuario;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
	    return "login";
	}
	
	@PostMapping("/logar2")
    public String logar2(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttrs) {
		return "redirect:/principal/";
    }
	
	@GetMapping("/logout")
    public String logout() {
		
        return "redirect:/login/";
    }
}
