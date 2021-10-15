package es.arbox.eGestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/principal")
public class PrincipalController {
	
	@GetMapping("/")
	public String principal(Model model) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = null;
//		if (principal instanceof UserDetails) {
//		  userDetails = (UserDetails) principal;
//		}
//		String userName = userDetails.getUsername();
		
	    return "/principal";
	}
}
