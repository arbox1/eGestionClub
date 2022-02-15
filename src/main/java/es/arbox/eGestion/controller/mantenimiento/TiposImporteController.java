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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.arbox.eGestion.controller.BaseController;
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
public class TiposImporteController extends BaseController{
	
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
    public String guardar(@ModelAttribute("tipo") TiposImporte tipo, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = tipo.getId() != null ? "actualizado" : "creado";
		tiposImporteService.guardar(tipo, getUsuarioLogado());
		
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
	
	@PostMapping(value = "/guardarSubtipo")
    public @ResponseBody RespuestaAjax guardarSubtipo(@ModelAttribute SubtiposImporte subTipos, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = subTipos.getId() != null ? "actualizado" : "realizado";
		
		subtiposImporteService.guardar(subTipos, getUsuarioLogado());
		
		result.setResultado("id", subTipos.getTipoImporte().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Subtipo %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/eliminarSubTipo")
    public @ResponseBody RespuestaAjax eliminarSubTipo(@ModelAttribute SubtiposImporte subTipos, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		subtiposImporteService.eliminar(SubtiposImporte.class, subTipos.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Subtipo eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/subTipo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String subTipo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		SubtiposImporte subtipo = subtiposImporteService.obtenerPorId(SubtiposImporte.class, valores.getId());
		
		result.setResultado("subTipo", subtipo.getMapa());
		
		return subtiposImporteService.serializa(result);
	}
	
}
