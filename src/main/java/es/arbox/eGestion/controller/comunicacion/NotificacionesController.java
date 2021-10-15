package es.arbox.eGestion.controller.comunicacion;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.service.socios.SociosCursoService;

@Controller
@RequestMapping("/comunicacion/notificaciones")
public class NotificacionesController {
	
	@Autowired
	SociosCursoService sociosCursoService;
	
	@Autowired
	MailService mailService;

	@GetMapping("/")
	public String listaSocios(Model model) {
		model.addAttribute("cursos", sociosCursoService.obtenerTodos(Curso.class));
		return "/comunicacion/notificaciones";
	}
	
	@PostMapping(value = "/recordatorio", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax recordatorio(@ModelAttribute Curso curso, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		curso = sociosCursoService.obtenerPorId(Curso.class, curso.getId());
		
		mailService.recordatorio(curso);
		
        return result;
    }
}
