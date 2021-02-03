package es.arbox.eGestion.controller.mantenimiento;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.economica.SubtiposImporte;
import es.arbox.eGestion.entity.economica.TiposImporte;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.economica.SubtiposImporteService;
import es.arbox.eGestion.service.economica.TiposImporteService;

@Controller
@RequestMapping("/mantenimiento/tiposImporte")
public class TiposImporteController {
	
	@Autowired
	private TiposImporteService tiposImporteService;
	
	@Autowired
	private SubtiposImporteService subtiposImporteService;

	@GetMapping("/")
	public String listaTipos(Model model) {
		model.addAttribute("tiposImporte", tiposImporteService.getTiposImporte());
		model.addAttribute("tipo", new TiposImporte());
		return "/mantenimiento/tiposImporte";
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("tipo") TiposImporte tipo, RedirectAttributes redirectAttrs) {
		String msg = tipo.getId() != null ? "actualizado" : "creado";
		tiposImporteService.guardar(tipo);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Tipo Importe %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/mantenimiento/tiposImporte/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("tipo") TiposImporte tipo, RedirectAttributes redirectAttrs) {
		tiposImporteService.eliminar(tipo.getClass(), tipo.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Tipo Ingreso eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/mantenimiento/tiposImporte/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		TiposImporte tipo = tiposImporteService.obtenerPorId(TiposImporte.class, valores.getId());
		
		result.setResultado("tipo", tipo.getMapa());
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/subTipos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String subTipos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<SubtiposImporte> subtipos = subtiposImporteService.getSubtiposImporte(valores.getId());
		
		result.setResultado("subtipos", SubtiposImporte.getListaMapa(subtipos));
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/guardarSubtipo")//, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCustomer(@RequestParam(value="id") Integer id) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException, JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		String msg = "";//subtipo.getId() != null ? "actualizado" : "creado";
//		subtiposImporteService.guardar(subtipo);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Subtipo Importe %1$s correctamente.", msg));
		
//		result.setResultado("subtipos", subtipo.getMapa());
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
    }
}
