package es.arbox.eGestion.controller.mantenimiento;

import java.lang.reflect.InvocationTargetException;

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
import es.arbox.eGestion.entity.documento.FamiliaDocumento;
import es.arbox.eGestion.entity.documento.TipoDocumento;
import es.arbox.eGestion.enums.TiposMensaje;

@Controller
@RequestMapping("/mantenimiento/tiposDocumentos")
public class TiposDocumentosController extends BaseController{
	
	@GetMapping("/")
	public String listaTiposDocumentos(Model model, @ModelAttribute("buscador") TipoDocumento tipoDocumento) {
		model.addAttribute("valor", new ValoresDTO());
		model.addAttribute("tipo", new TipoDocumento());
		model.addAttribute("tipos", menuService.obtenerTodos(TipoDocumento.class));
		model.addAttribute("familias", menuService.obtenerTodosOrden(FamiliaDocumento.class, " descripcion "));
		model.addAttribute("buscador", tipoDocumento == null ? new TipoDocumento() : tipoDocumento);
		return "/mantenimiento/tiposDocumentos";
	}
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		TipoDocumento tipo = menuService.obtenerPorId(TipoDocumento.class, valores.getId());
		result.setResultado("tipo", tipo.getMapa());
		
		return menuService.serializa(result);
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") TipoDocumento tipoDocumento, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = tipoDocumento.getId() != null ? "actualizado" : "creado";
		menuService.guardar(tipoDocumento);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Tipo %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/mantenimiento/tiposDocumentos/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") TipoDocumento tipoDocumento, RedirectAttributes redirectAttrs) {
		menuService.eliminar(tipoDocumento.getClass(), tipoDocumento.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Tipo de documento eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/mantenimiento/tiposDocumentos/";
    }
}
