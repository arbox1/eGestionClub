package es.arbox.eGestion.controller.servicios;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.entity.socios.Directiva;

@Controller
@RequestMapping("/servicios/elClub")
public class ElClubController extends BaseController{
	
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("directivas", menuService.obtenerTodosOrden(Directiva.class, "orden"));
		return "/servicios/elClub";
	}
}
