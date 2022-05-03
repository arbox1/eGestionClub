package es.arbox.eGestion.controller.servicios;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.EstadosParticipante;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.actividades.ActividadService;

@Controller
@RequestMapping("/servicios/actividadesServicio")
public class ActividadesServicioController extends BaseController {
	
	@Autowired
	ActividadService actividadService;

	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Actividad actividad) {
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("estadosParticipante", actividadService.obtenerTodosOrden(EstadosParticipante.class, " descripcion "));
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
	
	@ResponseBody
	@PostMapping(value = "/actividades", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getActividades(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Actividad actividad = new Actividad();
		TiposActividad tipo = new TiposActividad();
		tipo.setId(valores.getId());
		actividad.setTipo(tipo);
		actividad.setFechaFinPlazo(new Date());
		
		List<Actividad> lActividades = actividadService.getActividadesFiltro(actividad);
		
		result.setResultado("actividades", MenuEstructura.getListaMapa(lActividades));
		result.setResultado("ok", "S");
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@PostMapping(value = "/guardarParticipante")
    public @ResponseBody RespuestaAjax guardarParticipante(@ModelAttribute Participante participante, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = participante.getId() != null ? "actualizado" : "inscrito";
		
		if(participante.getId() == null) {
			participante.setFecha(new Date());
		}
		
		actividadService.guardar(participante, getUsuarioLogado());
		
		result.setResultado("id", participante.getActividad().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Participante %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
}
