package es.arbox.eGestion.controller.socios;

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

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.socios.Categoria;
import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.Socios;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.socios.CuotaService;
import es.arbox.eGestion.service.socios.SociosCursoService;
import es.arbox.eGestion.service.socios.SociosService;

@Controller
@RequestMapping("/socios/socios")
public class SociosController extends BaseController {
	
	@Autowired
	private SociosService sociosService;
	
	@Autowired
	private SociosCursoService sociosCursoService;
	
	@Autowired
	private CuotaService cuotaService;
	
	@GetMapping("/")
	public String listaSocios(Model model) {
		model.addAttribute("socios", sociosService.getSocios());
		model.addAttribute("cursos", sociosCursoService.obtenerTodos(Curso.class));
		model.addAttribute("escuelas", sociosCursoService.obtenerTodos(Escuela.class));
		model.addAttribute("categorias", sociosCursoService.obtenerTodos(Categoria.class));
		model.addAttribute("meses", sociosCursoService.obtenerTodos(Meses.class));
		model.addAttribute("nuevo", new Socios());
		return "/socios/socios";
	}
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") Socios socio, RedirectAttributes redirectAttrs) {
		String msg = socio.getId() != null ? "actualizado" : "creado";
		sociosService.guardar(socio);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Socio %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/socios/socios/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") Socios socio, RedirectAttributes redirectAttrs) {
		sociosService.eliminar(socio.getClass(), socio.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Socio eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/socios/socios/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Socios socio = sociosService.obtenerPorId(Socios.class, valores.getId());
		
		result.setResultado("socio", socio.getMapa());
		
		return sociosService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/escuelas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String escuelas(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<SociosCurso> sociosCurso = sociosCursoService.getSociosCurso(valores.getId());
		
		result.setResultado("sociosCurso", SociosCurso.getListaMapa(sociosCurso));
		
		return sociosService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/guardarInscripcion", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String guardarInscripcion(@RequestBody SociosCurso inscripcion, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = inscripcion.getId() != null ? "actualizada" : "realizada";
		
		sociosCursoService.guardar(inscripcion);
		
		result.setResultado("id", inscripcion.getSocio().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Inscripcion %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return sociosService.serializa(result);
    }
	
	@ResponseBody
	@PostMapping(value = "/eliminarInscripcion", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String eliminarInscripcion(@RequestBody SociosCurso inscripcion, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		sociosCursoService.eliminar(SociosCurso.class, inscripcion.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Inscripcion eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return sociosCursoService.serializa(result);
    }
	
	@ResponseBody
	@PostMapping(value = "/inscripcion", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String inscripcion(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		SociosCurso socioCurso = sociosCursoService.obtenerPorId(SociosCurso.class, valores.getId());
		
		result.setResultado("socioCurso", socioCurso.getMapa());
		
		return sociosCursoService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/cuotas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cuotas(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<Cuota> cuotas = cuotaService.getCuotas(valores.getId());
		
		result.setResultado("cuotas", SociosCurso.getListaMapa(cuotas));
		
		return cuotaService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/guardarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String guardarCuota(@RequestBody Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = cuota.getId() != null ? "actualizada" : "realizada";
		
		cuotaService.guardar(cuota);
		
		result.setResultado("id", cuota.getSocioCurso().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Cuota %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return cuotaService.serializa(result);
    }
	
	@ResponseBody
	@PostMapping(value = "/eliminarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String eliminarCuota(@RequestBody Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		cuotaService.eliminar(Cuota.class, cuota.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Cuota eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return cuotaService.serializa(result);
    }
	
	@ResponseBody
	@PostMapping(value = "/editarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String editarCuota(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Cuota cuota = cuotaService.obtenerPorId(Cuota.class, valores.getId());
		
		result.setResultado("cuota", cuota.getMapa());
		
		return cuotaService.serializa(result);
	}
}
