package es.arbox.eGestion.controller.economica;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.economica.Pago;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.economica.PagosService;

@Controller
@RequestMapping("/economica/pagos")
public class PagosController extends BaseController {
	
	@Autowired
	private PagosService pagosService;

	@GetMapping("/")
	public String listaSocios(Model model, @ModelAttribute("buscador") Pago pago) {
		model.addAttribute("nuevo", new Pago());
		model.addAttribute("pagos", new ArrayList<Pago>());
		model.addAttribute("usuarios", pagosService.obtenerTodosOrden(Usuario.class, " apellido1, apellido2, nombre "));
		model.addAttribute("buscador", pago == null ? new Pago() : pago);
		return "/economica/pagos";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Pago pago, RedirectAttributes redirectAttrs) {
		
		model.addAttribute("nuevo", new Pago());
		model.addAttribute("pagos", pagosService.getBusqueda(pago));
		model.addAttribute("usuarios", pagosService.obtenerTodosOrden(Usuario.class, " apellido1, apellido2, nombre "));
		model.addAttribute("buscador", pago);
		
		return "/economica/pagos";
    }
	
	@PostMapping(value = "/guardar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Pago pago, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = pago.getId() != null ? "actualizado" : "realizado";
		
		if(pago.getFecha() == null)
			pago.setFecha(new Date());
		pagosService.guardar(pago, getUsuarioLogado());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Pago %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax  eliminar(@ModelAttribute Pago pago, RedirectAttributes redirectAttrs) {
		RespuestaAjax result = new RespuestaAjax();
		pagosService.eliminar(pago.getClass(), pago.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Pago eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
}
