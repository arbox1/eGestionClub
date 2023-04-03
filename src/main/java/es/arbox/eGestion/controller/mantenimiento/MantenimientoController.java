package es.arbox.eGestion.controller.mantenimiento;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.BaseEntidad;
import es.arbox.eGestion.enums.TiposMensaje;

public abstract class MantenimientoController<T extends BaseEntidad> extends BaseController{
	
	@GetMapping("/")
	public String listaDatos(Model model) {
		model.addAttribute("valor", new ValoresDTO());
		model.addAttribute("dato", getDato());
		model.addAttribute("datos", menuService.obtenerTodos(getDato().getClass()));
		
		Map<String, Object> mapa = addModel();
		if (mapa != null) {
			for(String key : mapa.keySet()) {
				model.addAttribute(key, mapa.get(key));
			}
		}
	
		return getReturn();//"/mantenimiento/cursos";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		T t = (T) menuService.obtenerPorId(getDato().getClass(), valores.getId());
		result.setResultado("dato", t.getMapa());
		
		return menuService.serializa(result);
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") T t, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = t.getId() != null ? "actualizado" : "creado";
		menuService.guardar(t);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Dato %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return String.format("redirect:%1$s/", getReturn());
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") T t, RedirectAttributes redirectAttrs) {
		menuService.eliminar(t.getClass(), t.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Dato eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return String.format("redirect:%1$s/", getReturn());
    }
	
	public abstract Map<String, Object> addModel();
	
	public abstract String getReturn();
	
	public abstract T getDato();
}