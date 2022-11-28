package es.arbox.eGestion.controller.servicios;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.service.liga.LigaService;

@Controller
@RequestMapping("/servicios/ligasServicio")
public class LigasServicioController extends BaseController {
	
	@Autowired
	LigaService ligaService;

	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Liga liga) {
		model.addAttribute("ligas", ligaService.obtenerTodosFiltroOrden(Liga.class, " estado.id = 3 ", " id desc "));
		model.addAttribute("buscador", liga == null ? new Liga() : liga);
		return "/servicios/ligasServicio";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Liga liga, RedirectAttributes redirectAttrs) {
		model.addAttribute("ligas", ligaService.obtenerTodosFiltroOrden(Liga.class, " estado.id = 3 ", " id desc "));
		
		if(liga.getId() != null && ligaService.obtenerPorId(Liga.class, liga.getId()).getEstado().getId().equals(3)) {
			
			List<Grupo> grupos = liga.getId() == null ? new ArrayList<Grupo>() : ligaService.getDatosLiga(liga);
			model.addAttribute("grupos", grupos);
		}
		
		return "/servicios/ligasServicio";
    }
	
	@ResponseBody
	@PostMapping(value = "/grupos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String grupos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		Liga liga = new Liga();
		liga.setId(valores.getId());
		
		if(liga.getId() != null && ligaService.obtenerPorId(Liga.class, liga.getId()).getEstado().getId().equals(3)) {
			
			List<Grupo> grupos = liga.getId() == null ? new ArrayList<Grupo>() : ligaService.getDatosLiga(liga);
			result.setResultado("grupos", grupos);
		}
		
		return ligaService.serializa(result);
	}
	
	@PostMapping(value = "/resultadosEquipo", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String resultadosEquipo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		Equipo equipo = new Equipo();
		equipo.setId(valores.getId());
		
		if(equipo.getId() != null && ligaService.obtenerPorId(Equipo.class, equipo.getId()).getGrupo().getLiga().getEstado().getId().equals(3)) {
			result.setResultado("resultados", ligaService.getResultadosEquipo(equipo));
		}
		
		return ligaService.serializa(result);
    }
}
