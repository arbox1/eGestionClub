package es.arbox.eGestion.controller.liga;

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
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.liga.LigaService;

@Controller
@RequestMapping("/liga/resultados")
public class ResultadosController extends BaseController {
	
	@Autowired
	LigaService ligaService;
	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Liga liga) {
		model.addAttribute("ligas", ligaService.obtenerTodosOrden(Liga.class, " id desc "));
		model.addAttribute("grupos", new ArrayList<Grupo>());
		
		return "/liga/resultados";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Liga liga, RedirectAttributes redirectAttrs) {
		model.addAttribute("ligas", ligaService.obtenerTodosOrden(Liga.class, " id desc "));
		
		List<Grupo> grupos = liga.getId() == null ? new ArrayList<Grupo>() : ligaService.getDatosLiga(liga);
		
		model.addAttribute("grupos", grupos);
		return "/liga/resultados";
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		CalendarioLiga calendario = new CalendarioLiga();
		calendario.setId(valores.getId());
		CalendarioLiga calendarioBd = ligaService.getCalendarioLiga(calendario);
		
		result.setResultado("calendario", calendarioBd.getMapa());
		
		return ligaService.serializa(result);
    }
	
	@PostMapping(value = "/guardarResultado")
    public @ResponseBody RespuestaAjax guardarResultado(@ModelAttribute Resultado resultado, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		if(resultado.getId() == null) {
			mensajes.mensaje(TiposMensaje.danger, "No existe resultado para este encuentro.");
			result.setResultado("ok", "N");
		} else {
			
			Resultado r = ligaService.obtenerPorId(resultado.getClass(), resultado.getId());
			r.setResultadoa(resultado.getResultadoa());
			r.setResultadob(resultado.getResultadob());
			
			ligaService.guardar(r, getUsuarioLogado());
			
			CalendarioLiga calendario = ligaService.obtenerPorId(CalendarioLiga.class, r.getId());
			calendario.setNoPresentadoa(resultado.getCalendario()!= null && resultado.getCalendario().getNoPresentadoa() != null ? "S" : null);
			calendario.setNoPresentadob(resultado.getCalendario()!= null && resultado.getCalendario().getNoPresentadob() != null ? "S" : null);
			calendario.setComentario(resultado.getCalendario()!= null && resultado.getCalendario().getComentario() != null ? resultado.getCalendario().getComentario() : null);
			ligaService.guardar(calendario, getUsuarioLogado());
			
			result.setResultado("resultado", r);
			result.setResultado("ok", "S");
			
			mensajes.mensaje(TiposMensaje.success, "Resultado almacenado correctamente.");
		}
		
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/resultadosEquipo", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String resultadosEquipo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		Equipo equipo = new Equipo();
		equipo.setId(valores.getId());
		
		result.setResultado("resultados", ligaService.getResultadosEquipo(equipo));
		
		return ligaService.serializa(result);
    }
}
