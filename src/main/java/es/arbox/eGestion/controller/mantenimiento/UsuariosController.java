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
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/mantenimiento/usuarios")
public class UsuariosController extends BaseController {
	
	@GetMapping("/")
	public String listaSocios(Model model) {
		model.addAttribute("usuarios", menuService.obtenerTodos(Usuario.class));
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("valor", new ValoresDTO());
		return "/mantenimiento/usuarios";
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") Usuario usuario, RedirectAttributes redirectAttrs) {
		String msg = usuario.getId() != null ? "actualizado" : "creado";
		usuario.setPassword(Utilidades.getMd5(usuario.getPassword()));
		menuService.guardar(usuario);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Usuario %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/mantenimiento/usuarios/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") Usuario usuario, RedirectAttributes redirectAttrs) {
		menuService.eliminar(usuario.getClass(), usuario.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Usuario eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/mantenimiento/usuarios/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Usuario usuario = menuService.obtenerPorId(Usuario.class, valores.getId());
		usuario.setPassword("");
		result.setResultado("usuario", usuario.getMapa());
		
		return menuService.serializa(result);
	}
}
