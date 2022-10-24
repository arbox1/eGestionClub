package es.arbox.eGestion.controller.socios;

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
import es.arbox.eGestion.entity.economica.Tarifa;
import es.arbox.eGestion.entity.socios.AsistenciaMonitor;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.socios.AsistenciaMonitorService;

@Controller
@RequestMapping("/socios/asistencia")
public class AsistenciaController extends BaseController{
	
	@Autowired
	AsistenciaMonitorService asistenciaMonitorService;
	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") AsistenciaMonitor aistencia) {
		model.addAttribute("asistencia", new ArrayList<AsistenciaMonitor>());
		model.addAttribute("tarifas", asistenciaMonitorService.obtenerTodosOrden(Tarifa.class, "descripcion"));
		model.addAttribute("escuelas", asistenciaMonitorService.obtenerTodosOrden(Escuela.class, "descripcion"));
		model.addAttribute("buscador", aistencia == null ? new AsistenciaMonitor() : aistencia);
		return "/socios/asistencia";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") AsistenciaMonitor asistencia, RedirectAttributes redirectAttrs) {
		model.addAttribute("tarifas", asistenciaMonitorService.obtenerTodosOrden(Tarifa.class, "descripcion"));
		model.addAttribute("escuelas", asistenciaMonitorService.obtenerTodosOrden(Escuela.class, "descripcion"));

		asistencia.setMonitor(getUsuarioLogado());
		List<AsistenciaMonitor> asistencias = asistenciaMonitorService.getAsistencias(asistencia);
		
		model.addAttribute("asistencias", asistencias);
		return "/socios/asistencia";
    }
	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute AsistenciaMonitor asistencia, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		asistenciaMonitorService.eliminar(AsistenciaMonitor.class, asistencia.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Asistencia eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute AsistenciaMonitor asistencia, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = asistencia.getId() != null ? "actualizada" : "realizada";
		
		asistencia.setMonitor(getUsuarioLogado());
		
		asistenciaMonitorService.guardar(asistencia, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Asistencia %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		AsistenciaMonitor asistencia = asistenciaMonitorService.obtenerPorId(AsistenciaMonitor.class, valores.getId());		
		
		result.setResultado("asistencia", asistencia.getMapa());
		
		return asistenciaMonitorService.serializa(result);
    }
}
