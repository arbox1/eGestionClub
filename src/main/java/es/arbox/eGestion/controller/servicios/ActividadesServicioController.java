package es.arbox.eGestion.controller.servicios;

import java.util.Date;

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
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.service.actividades.ActividadService;

@Controller
@RequestMapping("/servicios/actividadesServicio")
public class ActividadesServicioController extends BaseController {
	
	@Autowired
	ActividadService actividadService;

	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Actividad actividad) {
		actividad.setFechaFinPlazo(new Date());
		model.addAttribute("actividades", actividadService.getActividadesFiltro(actividad));
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("buscador", actividad == null ? new Actividad() : actividad);
		return "/servicios/actividadesServicio";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Actividad actividad, RedirectAttributes redirectAttrs) {
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		
		actividad.setFechaFinPlazo(new Date());
		model.addAttribute("actividades", actividadService.getActividadesFiltro(actividad));
		return "/servicios/actividadesServicio";
    }
}
