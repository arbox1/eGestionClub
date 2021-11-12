package es.arbox.eGestion.controller.socios;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

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
import com.twilio.rest.api.v2010.account.Message;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.socios.Calendario;
import es.arbox.eGestion.entity.socios.Categoria;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.WhatsappsService;
import es.arbox.eGestion.service.socios.CalendarioService;
import es.arbox.eGestion.service.socios.SociosCursoService;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/socios/calendario")
public class CalendarioController extends BaseController{
	
	@Autowired
	CalendarioService calendarioService;
	
	@Autowired
	SociosCursoService sociosCursoService;
	
	@Autowired
	WhatsappsService whatsappsService;

	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Calendario calendario) {
		model.addAttribute("calendarios", new ArrayList<Calendario>());
		model.addAttribute("cursos", calendarioService.obtenerTodos(Curso.class));
		model.addAttribute("escuelas", calendarioService.obtenerTodos(Escuela.class));
		model.addAttribute("categorias", calendarioService.obtenerTodos(Categoria.class));
		model.addAttribute("buscador", calendario == null ? new Calendario() : calendario);
		return "/socios/calendario";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Calendario calendario, RedirectAttributes redirectAttrs) {
		model.addAttribute("cursos", calendarioService.obtenerTodos(Curso.class));
		model.addAttribute("escuelas", calendarioService.obtenerTodos(Escuela.class));
		model.addAttribute("categorias", calendarioService.obtenerTodos(Categoria.class));
		
		List<Calendario> calendarios = calendarioService.getCalendarios(calendario);
		
		model.addAttribute("calendarios", calendarios);
		return "/socios/calendario";
    }
	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute Calendario calendario, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		calendarioService.eliminar(Calendario.class, calendario.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Encuentro eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Calendario calendario, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = calendario.getId() != null ? "actualizado" : "realizado";
		
		if(!StringUtils.isEmpty(calendario.getHoraInicio())) {
			calendario.setFecha(Utilidades.asignarHora(calendario.getFecha(), calendario.getHoraInicio()));
		}
		
		if(!StringUtils.isEmpty(calendario.getHoraSalida())) {
			calendario.setFechaSalida(Utilidades.asignarHora(calendario.getFecha(), calendario.getHoraSalida()));
		}
		
		calendarioService.guardar(calendario);
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Encuentro %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		Calendario calendario = calendarioService.obtenerPorId(Calendario.class, valores.getId());		
		
		result.setResultado("calendario", calendario.getMapa());
		
		return calendarioService.serializa(result);
    }
	
	@PostMapping(value = "/notificar")
    public @ResponseBody RespuestaAjax notificar(@ModelAttribute Calendario calendario, RedirectAttributes redirectAttrs) throws JsonProcessingException, MessagingException {
		RespuestaAjax result = new RespuestaAjax();
		
		calendario = calendarioService.obtenerPorId(Calendario.class, calendario.getId());
		
		List<SociosCurso> sociosCurso = sociosCursoService.obtenerSociosFiltro(
				calendario.getCurso().getId()/*Curso*/,
				calendario.getEscuela().getId()/*Escuela*/,
				calendario.getCategoria().getId()/*Categoría*/);
		
		StringBuilder mensaje = new StringBuilder();
		
		mensaje.append(String.format("Información próximo partido: \n\n"
				+ "Rival: %1$s\n"
				+ "Color Rival: %2$s\n"
				+ "Fecha: %3$s\n"
				+ "Hora: %4$s\n"
				+ "Lugar: %5$s\n"
				+ "Hora de salida: %6$s\n"
				+ "Lugar de salida: %7$s\n", 
				calendario.getRival(),
				calendario.getColorRival(),
				Utilidades.formatDateToString(calendario.getFecha()),
				Utilidades.formatDateToStringHora(calendario.getFecha()),
				calendario.getLugar(),
				Utilidades.formatDateToStringHora(calendario.getFechaSalida()),
				calendario.getLugarSalida()
				));
		
		for(SociosCurso socioCurso : sociosCurso) {
			if(!StringUtils.isEmpty(socioCurso.getSocio().getTelefono())){
				String telefono = "+34" + socioCurso.getSocio().getTelefono().replace(" ","");
				Message message = whatsappsService.enviarMensaje(mensaje.toString(), telefono);	
				result.setResultado("id", message.getSid());
			}
		}
        
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Notificación realizada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
}
