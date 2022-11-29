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

import es.arbox.eGestion.comparator.RandomEquipoComparator;
import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.EstadosActividad;
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Jornada;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.liga.LigaService;
import es.arbox.eGestion.service.utils.CalendarioGeneratorService;

@Controller
@RequestMapping("/liga/ligas")
public class LigasController extends BaseController {
	
	@Autowired
	LigaService ligaService;
	
	@Autowired
	CalendarioGeneratorService calendarioGeneratorService;
	
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
	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute Liga liga, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		ligaService.eliminar(Liga.class, liga.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Liga eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/calendario")
    public @ResponseBody RespuestaAjax calendario(@ModelAttribute Liga liga, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		List<Grupo> grupos = ligaService.getDatosLiga(liga);
		
		for(Grupo grupo : grupos) {
			for(Jornada jornada : grupo.getJornadas()) {
				for(CalendarioLiga calendario : jornada.getCalendarios()) {
					for(Resultado resultado : calendario.getResultados()) {
						ligaService.eliminar(Resultado.class, resultado.getId());
					}
					ligaService.eliminar(CalendarioLiga.class, calendario.getId());
				}
				ligaService.eliminar(Jornada.class, jornada.getId());
			}
			grupo.setJornadas(new ArrayList<>());
			List<Equipo> equipos = ligaService.obtenerTodosFiltroOrden(Equipo.class, String.format(" grupo.id = %1$s ", grupo.getId()), "descripcion");
			equipos.sort(new RandomEquipoComparator());
			grupo.setEquipos(equipos);
		}
		
		List<Grupo> gruposResultado = calendarioGeneratorService.calendario(grupos);
		
		for(Grupo grupo : gruposResultado) {
			for(Jornada jornada : grupo.getJornadas()) {
				ligaService.guardar(jornada);
				for(CalendarioLiga calendario : jornada.getCalendarios()) {
					ligaService.guardar(calendario);
					for(Resultado resultado : calendario.getResultados()) {
						ligaService.guardar(resultado);
					}
				}
			}
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Calendario generado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminarCalendario")
    public @ResponseBody RespuestaAjax eliminarCalendario(@ModelAttribute Liga liga, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		List<Grupo> grupos = ligaService.getDatosLiga(liga);
		
		for(Grupo grupo : grupos) {
			for(Jornada jornada : grupo.getJornadas()) {
				for(CalendarioLiga calendario : jornada.getCalendarios()) {
					for(Resultado resultado : calendario.getResultados()) {
						ligaService.eliminar(Resultado.class, resultado.getId());
					}
					ligaService.eliminar(CalendarioLiga.class, calendario.getId());
				}
				ligaService.eliminar(Jornada.class, jornada.getId());
			}
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Calendario eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Liga liga, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = liga.getId() != null ? "actualizada" : "creada";
		
		ligaService.guardar(liga, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Liga %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		Liga liga = ligaService.obtenerPorId(Liga.class, valores.getId());		
		
		result.setResultado("liga", liga.getMapa());
		
		return ligaService.serializa(result);
    }
	
	@PostMapping(value = "/grupos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String grupos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<Grupo> grupos = ligaService.obtenerTodosFiltroOrden(Grupo.class, String.format(" liga.id = %1$s ", valores.getId()), "descripcion");
		
		result.setResultado("grupos", Grupo.getListaMapa(grupos));
		
		return ligaService.serializa(result);
	}
	
	@PostMapping(value = "/guardarGrupo")
    public @ResponseBody RespuestaAjax guardarGrupo(@ModelAttribute Grupo grupo, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = grupo.getId() != null ? "actualizado" : "creado";
		
		ligaService.guardar(grupo, getUsuarioLogado());
		
		result.setResultado("id", grupo.getLiga().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Grupo %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/eliminarGrupo")
    public @ResponseBody RespuestaAjax eliminarGrupo(@ModelAttribute Grupo grupo, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		ligaService.eliminar(Grupo.class, grupo.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Grupo eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/grupo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String grupo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Grupo grupo = ligaService.obtenerPorId(Grupo.class, valores.getId());
		
		result.setResultado("grupo", grupo.getMapa());
		
		return ligaService.serializa(result);
	}
	
	@PostMapping(value = "/equipos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String equipos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<Equipo> equipos = ligaService.obtenerTodosFiltroOrden(Equipo.class, String.format(" grupo.liga.id = %1$s ", valores.getId()), "grupo.descripcion, descripcion");
		
		result.setResultado("equipos", Equipo.getListaMapa(equipos));
		
		return ligaService.serializa(result);
	}
	
	@PostMapping(value = "/guardarEquipo")
    public @ResponseBody RespuestaAjax guardarEquipo(@ModelAttribute Equipo equipo, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = equipo.getId() != null ? "actualizado" : "creado";
		
		ligaService.guardar(equipo, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Equipo %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/eliminarEquipo")
    public @ResponseBody RespuestaAjax eliminarEquipo(@ModelAttribute Equipo equipo, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		ligaService.eliminar(Equipo.class, equipo.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Equipo eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/equipo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String equipo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Equipo equipo = ligaService.obtenerPorId(Equipo.class, valores.getId());
		
		result.setResultado("equipo", equipo.getMapa());
		
		return ligaService.serializa(result);
	}
	
	@PostMapping(value = "/selectGrupo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String selectGrupo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<Grupo> grupos = ligaService.obtenerTodosFiltroOrden(Grupo.class, String.format(" liga.id = %1$s ", valores.getId()), "descripcion");
		
		result.setResultado("datos", Grupo.getListaMapa(grupos));
		
		return ligaService.serializa(result);
	}
}
