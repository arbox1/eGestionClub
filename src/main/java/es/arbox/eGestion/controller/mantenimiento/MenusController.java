package es.arbox.eGestion.controller.mantenimiento;

import java.lang.reflect.InvocationTargetException;

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
import es.arbox.eGestion.entity.config.Menu;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.entity.config.MenuRol;
import es.arbox.eGestion.entity.config.Rol;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.MenuRolService;

@Controller
@RequestMapping("/mantenimiento/menus")
public class MenusController extends BaseController {
	
	@Autowired
	MenuRolService menuRolService;
	
	@GetMapping("/")
	public String listaSocios(Model model) {
		model.addAttribute("menus", menuService.obtenerTodosOrden(Menu.class, " menuEstructura.orden, orden "));
		model.addAttribute("menusEstructura", menuService.obtenerTodos(MenuEstructura.class));
		model.addAttribute("roles", menuService.obtenerTodosOrden(Rol.class, " descripcion "));
		model.addAttribute("menu", new Menu());
		model.addAttribute("valor", new ValoresDTO());
		return "/mantenimiento/menus";
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") Menu menu, RedirectAttributes redirectAttrs) {
		String msg = menu.getId() != null ? "actualizado" : "creado";
		menuService.guardar(menu);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Menu %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/mantenimiento/menus/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") Menu menu, RedirectAttributes redirectAttrs) {
		menuService.eliminar(menu.getClass(), menu.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Menu eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/mantenimiento/menus/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Menu menu = menuService.obtenerPorId(Menu.class, valores.getId());
		result.setResultado("menu", menu.getMapa());
		
		return menuService.serializa(result);
	}
	
	@PostMapping(value = "/roles", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String roles(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Menu menu = new Menu();
		menu.setId(valores.getId());
		
		result.setResultado("roles", menuRolService.getMenuRol(menu));
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@PostMapping(value = "/guardarRol")
    public @ResponseBody RespuestaAjax guardarRol(@ModelAttribute MenuRol menuRol, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = menuRol.getId() != null ? "actualizado" : "añadido";

		menuService.guardar(menuRol);
		
		result.setResultado("id", menuRol.getMenu().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Rol %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/eliminarRol")
    public @ResponseBody RespuestaAjax eliminarRol(@ModelAttribute MenuRol menuRol, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		menuService.eliminar(MenuRol.class, menuRol.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Rol eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
}
