package es.arbox.eGestion.controller.socios;

import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
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
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.socios.Categoria;
import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.DocumentoSocio;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.Socios;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.service.socios.CuotaService;
import es.arbox.eGestion.service.socios.DocumentoSocioService;
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
	
	@Autowired
	private DocumentoSocioService documentoSocioService;
	
	@Autowired
    private MailService mailService;
	
	@GetMapping("/")
	public String listaSocios(Model model, @ModelAttribute("buscador") Socios socio) {
		return buscar(model, socio);
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Socios socio) {
		model.addAttribute("socios", sociosService.getBusqueda(socio));
		model.addAttribute("cursos", sociosCursoService.obtenerTodos(Curso.class));
		model.addAttribute("escuelas", sociosCursoService.obtenerTodos(Escuela.class));
		model.addAttribute("categorias", sociosCursoService.obtenerTodos(Categoria.class));
		model.addAttribute("tiposDocumentos", documentoSocioService.getTipoDocumento(FamiliasDocumento.SOCIO));
		model.addAttribute("meses", sociosCursoService.obtenerTodos(Meses.class));
		model.addAttribute("nuevo", new Socios());
		model.addAttribute("valor", new ValoresDTO());
		model.addAttribute("buscador", socio);
		
		return "/socios/socios";
    }
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") Socios socio, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = socio.getId() != null ? "actualizado" : "creado";
		sociosService.guardar(socio, getUsuarioLogado());
		
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
		
		DocumentoSocio documentoSocio = documentoSocioService.getDocumentoFoto(valores.getId());
		
		if(documentoSocio != null) {
			result.setResultado("imagen", Base64.getEncoder().encodeToString(documentoSocio.getDocumento().getFichero()));
			result.setResultado("mime", documentoSocio.getDocumento().getMime());
		}
		
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
	
	@PostMapping(value = "/guardarInscripcion")
    public @ResponseBody RespuestaAjax guardarInscripcion(@ModelAttribute SociosCurso inscripcion, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = inscripcion.getId() != null ? "actualizada" : "realizada";
		
		if(inscripcion.getEntrada() != null && inscripcion.getEntrada().getId() == null) {
			inscripcion.setEntrada(null);
		}
		
		if(inscripcion.getSalida() != null && inscripcion.getSalida().getId() == null) {
			inscripcion.setSalida(null);
		}
		
		sociosCursoService.guardar(inscripcion, getUsuarioLogado());
		
		result.setResultado("id", inscripcion.getSocio().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Inscripcion %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/eliminarInscripcion")
    public @ResponseBody RespuestaAjax eliminarInscripcion(@ModelAttribute SociosCurso inscripcion, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		sociosCursoService.eliminar(SociosCurso.class, inscripcion.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Inscripcion eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
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
	
	@PostMapping(value = "/guardarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarCuota(@ModelAttribute Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = cuota.getId() != null ? "actualizada" : "realizada";
		
		cuotaService.guardar(cuota, getUsuarioLogado());
		
		result.setResultado("id", cuota.getSocioCurso().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Cuota %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/enviarMailCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax enviarMailCuota(@ModelAttribute Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		cuota = cuotaService.obtenerPorId(Cuota.class, cuota.getId());
		
		if(!StringUtils.isEmpty(cuota.getSocioCurso().getSocio().getEmail())) {
			mailService.correoPagoSocio(cuota);
			
	        cuota.setNotificado("S");
	        cuotaService.guardar(cuota, getUsuarioLogado());
	        mensajes.mensaje(TiposMensaje.success, String.format("Correo enviado corréctamente"));
		} else {
			mensajes.mensaje(TiposMensaje.danger, "El socio no tiene dirección de correos");
		}
        
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax eliminarCuota(@ModelAttribute Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		cuotaService.eliminar(Cuota.class, cuota.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Cuota eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/editarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String editarCuota(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Cuota cuota = cuotaService.obtenerPorId(Cuota.class, valores.getId());
		
		result.setResultado("cuota", cuota.getMapa());
		
		return cuotaService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/documentos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String documentos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<DocumentoSocio> documentoSocio = documentoSocioService.getDocumentos(valores.getId());
		
		result.setResultado("documento", DocumentoSocio.getListaMapa(documentoSocio));
		
		return documentoSocioService.serializa(result);
	}
	
	@PostMapping(value = "/guardarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarDocumento(@ModelAttribute DocumentoSocio documentoSocio, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = documentoSocio.getId() != null ? "actualizado" : "realizado";
		
		documentoSocioService.guardar(documentoSocio.getDocumento(), getUsuarioLogado());
		documentoSocioService.guardar(documentoSocio);
		
		result.setResultado("id", documentoSocio.getSocio().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Documento %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax eliminarDocumento(@ModelAttribute DocumentoSocio documentoSocio, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		Integer idDocumento = documentoSocio.getDocumento().getId();

		documentoSocioService.eliminar(DocumentoSocio.class, documentoSocio.getId());
		documentoSocioService.eliminar(Documento.class, idDocumento);
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Documento eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
}
