package es.arbox.eGestion.controller.servicios;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.entity.socios.Directiva;
import es.arbox.eGestion.entity.socios.Patrocinador;

@Controller
@RequestMapping("/servicios/elClub")
public class ElClubController extends BaseController{
	
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("directivas", menuService.obtenerTodosOrden(Directiva.class, "orden"));
		model.addAttribute("patrocinadores", menuService.obtenerTodosFiltroOrden(Patrocinador.class, " fechaBaja is null ", " id "));
		return "/servicios/elClub";
	}
}
