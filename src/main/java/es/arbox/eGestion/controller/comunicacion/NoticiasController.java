package es.arbox.eGestion.controller.comunicacion;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import es.arbox.eGestion.entity.comunicacion.Noticia;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.comunicacion.NoticiaService;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/comunicacion/noticias")
public class NoticiasController extends BaseController {
	
	@Autowired
	public NoticiaService noticiaService;

	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Noticia noticia) {
		model.addAttribute("noticias", new ArrayList<Noticia>());
		model.addAttribute("buscador", noticia == null ? new Noticia() : noticia);
		return "/comunicacion/noticias";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Noticia noticia, RedirectAttributes redirectAttrs) {
		List<Noticia> noticias = noticiaService.getNoticiasFiltro(noticia);
		
		model.addAttribute("noticias", noticias);
		return "/comunicacion/noticias";
    }
	

	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute Noticia noticia, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		noticiaService.eliminar(Noticia.class, noticia.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Noticia eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Noticia noticia, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = noticia.getId() != null ? "actualizada" : "realizada";
		
		if(!StringUtils.isEmpty(noticia.getHora())) {
			noticia.setFecha(Utilidades.asignarHora(noticia.getFecha(), noticia.getHora()));
		}
		
		noticiaService.guardar(noticia, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Noticia %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		Noticia noticia = noticiaService.obtenerPorId(Noticia.class, valores.getId());		
		
		result.setResultado("noticia", noticia.getMapa());
		
		return noticiaService.serializa(result);
    }
}
