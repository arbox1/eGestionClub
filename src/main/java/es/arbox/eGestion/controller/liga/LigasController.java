package es.arbox.eGestion.controller.liga;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.EstadosActividad;
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.service.liga.LigaService;

@Controller
@RequestMapping("/liga/ligas")
public class LigasController extends BaseController {
	
	@Autowired
	LigaService ligaService;
	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Liga liga) {
		model.addAttribute("ligas", new ArrayList<Actividad>());
		model.addAttribute("tipos", ligaService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("buscador", liga == null ? new Liga() : liga);
		model.addAttribute("estados", ligaService.obtenerTodosOrden(EstadosActividad.class, " descripcion "));
		
		return "/liga/ligas";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Liga liga, RedirectAttributes redirectAttrs) {
		model.addAttribute("tipos", ligaService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("buscador", liga == null ? new Liga() : liga);
		model.addAttribute("estados", ligaService.obtenerTodosOrden(EstadosActividad.class, " descripcion "));
		
		List<Liga> ligas = ligaService.getLigasFiltro(liga);
		
		model.addAttribute("ligas", ligas);
		return "/liga/ligas";
    }
}
