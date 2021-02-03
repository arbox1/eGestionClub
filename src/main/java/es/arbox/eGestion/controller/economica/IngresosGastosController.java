package es.arbox.eGestion.controller.economica;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

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
import es.arbox.eGestion.entity.economica.IngresosGastos;
import es.arbox.eGestion.entity.economica.SubtiposImporte;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.economica.IngresosGastosService;

@Controller
@RequestMapping("/economica/ingresosGastos")
public class IngresosGastosController extends BaseController {
	
	@Autowired
	IngresosGastosService ingresosGastosService;
	
	@GetMapping("/")
	public String listaSocios(Model model) {
		model.addAttribute("ingresos", ingresosGastosService.obtenerTodos(IngresosGastos.class));
		model.addAttribute("tipos", ingresosGastosService.obtenerTodos(SubtiposImporte.class));
		model.addAttribute("nuevo", new IngresosGastos());
		return "/economica/ingresosGastos";
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") IngresosGastos ingresoGasto, RedirectAttributes redirectAttrs) {
		String msg = ingresoGasto.getId() != null ? "actualizado" : "creado";
		if (ingresoGasto.getFecha() == null)	
			ingresoGasto.setFecha(new Date());
		ingresosGastosService.guardar(ingresoGasto);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Ingreso / Gasto %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/economica/ingresosGastos/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") IngresosGastos ingresoGasto, RedirectAttributes redirectAttrs) {
		ingresosGastosService.eliminar(ingresoGasto.getClass(), ingresoGasto.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Ingreso / Gasto eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/economica/ingresosGastos/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		IngresosGastos ingresoGasto = ingresosGastosService.obtenerPorId(IngresosGastos.class, valores.getId());
		
		result.setResultado("ingresoGasto", ingresoGasto.getMapa());
		
		return ingresosGastosService.serializa(result);
	}
}
