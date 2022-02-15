package es.arbox.eGestion.controller.comunicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.socios.Categoria;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.service.socios.SociosCursoService;

@Controller
@RequestMapping("/comunicacion/notificaciones")
public class NotificacionesController extends BaseController {
	
	@Autowired
	SociosCursoService sociosCursoService;
	
	@Autowired
	MailService mailService;

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("cursos", sociosCursoService.obtenerTodos(Curso.class));
		model.addAttribute("categorias", sociosCursoService.obtenerTodos(Categoria.class));
		model.addAttribute("escuelas", sociosCursoService.obtenerTodos(Escuela.class));
		return "/comunicacion/notificaciones";
	}
	
	@PostMapping(value = "/recordatorio", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax recordatorio(@ModelAttribute SociosCurso socioCurso, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		mailService.recordatorio(socioCurso);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Notificación realizada corréctamente"));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping("/informe")
	public ModelAndView informe(@ModelAttribute ValoresDTO valores) throws IOException{
		List<ValoresDTO> datos = new ArrayList<>();
		datos.add(valores);
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		mapa.put("param", "prueba");
		
		return getInforme(valores.getDescripcion(), "prueba", datos, mapa);
	}

}
