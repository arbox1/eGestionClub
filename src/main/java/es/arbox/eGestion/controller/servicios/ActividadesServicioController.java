package es.arbox.eGestion.controller.servicios;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.entity.actividades.DocumentoParticipante;
import es.arbox.eGestion.entity.actividades.EstadosActividad;
import es.arbox.eGestion.entity.actividades.EstadosParticipante;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.enums.EstadosParticipantes;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.actividades.ActividadService;
import es.arbox.eGestion.service.actividades.DocumentoActividadService;
import es.arbox.eGestion.service.actividades.DocumentoParticipanteService;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.utils.PasswordGenerator;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/servicios/actividadesServicio")
public class ActividadesServicioController extends BaseController {
	
	@Autowired
	ActividadService actividadService;
	
	@Autowired
	DocumentoActividadService documentoActividadService;
	
	@Autowired
	DocumentoParticipanteService documentoParticipanteService;
	
	@Autowired
	MailService mailService;

	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Actividad actividad) {
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("estadosParticipante", actividadService.obtenerTodosOrden(EstadosParticipante.class, " descripcion "));
		model.addAttribute("buscador", actividad == null ? new Actividad() : actividad);
		return "/servicios/actividadesServicio";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Actividad actividad, RedirectAttributes redirectAttrs) {
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		model.addAttribute("actividades", actividadService.getActividadesFiltro(actividad));
		return "/servicios/actividadesServicio";
    }
	
	@ResponseBody
	@PostMapping(value = "/actividades", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getActividades(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Actividad actividad = new Actividad();
		TiposActividad tipo = new TiposActividad();
		tipo.setId(valores.getId());
		actividad.setTipo(tipo);
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		
		List<Actividad> lActividades = actividadService.getActividadesFiltro(actividad);
		
		for(Actividad a : lActividades) {
			a.setPermiso(a.getFechaFinPlazo() != null && a.getFechaFinPlazo().after(new Date()) ? "S" : "N");
		}
		
		result.setResultado("actividades", MenuEstructura.getListaMapa(lActividades));
		result.setResultado("ok", "S");
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@PostMapping(value = "/guardarParticipante")
    public @ResponseBody RespuestaAjax guardarParticipante(@ModelAttribute Participante participante, @ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		ValoresDTO v = new ValoresDTO();
		getCaptcha(v);
		result.setResultado("valor", v.getMapa());
		
		
		Actividad actividad = participante.getActividad();
		actividad.setFechaFinPlazo(new Date());
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		if(!valores.getHiddenCaptcha().equals(valores.getCaptcha())) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("El captcha introducido no es correcto."));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		String opcion = participante.getId() != null ? "actualizado" : "inscrito";
		
		String password = PasswordGenerator.getPassword(10);
		
		participante.setId(null);
		participante.setEstado(new EstadosParticipante(EstadosParticipantes.SOLICITADA));
		participante.setFecha(new Date());
		participante.setImporte(null);
		participante.setPagado(null);
		participante.setPassword(Utilidades.getMd5(password));
		
		actividadService.guardar(participante);
		
		mailService.correoInscripcionParticipante(actividadService.obtenerPorId(Participante.class, participante.getId()), password);
		
		result.setResultado("ok", "S");
		
		mensajes.mensaje(TiposMensaje.success, String.format("Participante %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/consultar")
    public @ResponseBody RespuestaAjax consultar(@ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		Actividad actividad = new Actividad();
		actividad.setId(valores.getId());
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		Participante p = new Participante();
		Actividad a = new Actividad();
		a.setId(valores.getId());
		p.setActividad(a);
		p.setEmail(valores.getNombre());
		p.setPassword(valores.getPassword());
		
		Participante resultado = actividadService.getParticipantePassword(p);
		
		
		if(!valores.getHiddenCaptcha().equals(valores.getCaptcha())) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("El captcha introducido no es correcto."));
			result.setMensajes(mensajes.getMensajes());
		} else if(resultado != null) {
			List<DocumentoParticipante> documentoParticipante = documentoParticipanteService.getDocumentos(resultado.getId());
			
			result.setResultado("permiso", resultado.getEstado() != null && resultado.getEstado().getId() == 6 ? "S" : "N");
			result.setResultado("participante", resultado.getMapa());
			result.setResultado("documento", DocumentoParticipante.getListaMapa(documentoParticipante));
			result.setResultado("ok", "S");
		} else {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("Usuario o contraseña incorrectos."));
			result.setMensajes(mensajes.getMensajes());
		}
		
		getCaptcha(valores);
		result.setResultado("valor", valores.getMapa());
		
		return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/documentos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String documentos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		Actividad actividad = new Actividad();
		actividad.setId(valores.getId());
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return documentoActividadService.serializa(result);
		}
		
		List<DocumentoActividad> documentoActividad = documentoActividadService.getDocumentosPorTipo(valores.getId(), TiposDocumento.ANTES_ACTIVIDAD);
		
		result.setResultado("documento", DocumentoActividad.getListaMapa(documentoActividad));
		
		return documentoActividadService.serializa(result);
	}
	
	@PostMapping("/documento")
	public HttpEntity<byte[]> getDocumento(@ModelAttribute("valor") ValoresDTO valores) throws IOException {
		Actividad actividad = documentoActividadService.getDocumentoActividad(valores.getId()).getActividad();
		
		if(actividad.getEstado() == null || actividad.getEstado().getId() != 1) {
			return null;
		}
		
		return super.getDocumento(valores);
	}
	
	private boolean permiso(Actividad actividad) {
		List<Actividad> actividades = actividadService.getActividadesFiltro(actividad);
		
		return actividades != null && actividades.size()>0 ? true : false;
	}
	
	@PostMapping("/documentoParticipante")
	public HttpEntity<byte[]> getDocumentoParticipante(@ModelAttribute("valor") ValoresDTO valores) throws IOException {
		
		Actividad actividad = documentoParticipanteService.getDocumentoParticipante(valores.getId()).getParticipante().getActividad();
		
		if(actividad.getEstado() == null || actividad.getEstado().getId() != 1) {
			return null;
		}
		
		return super.getDocumento(valores);
	}
	
	@PostMapping(value = "/guardarDocumentoParticipante", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarDocumentoParticipante(@ModelAttribute DocumentoParticipante documentoParticipante, @ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		Participante p = documentoParticipanteService.obtenerPorId(Participante.class, documentoParticipante.getParticipante().getId());
		
		Actividad actividad = p.getActividad();
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		p.setEmail(valores.getNombre());
		p.setPassword(valores.getPassword());
		Participante resultado = actividadService.getParticipantePassword(p);
		
		if (resultado != null && resultado.getEstado().getId() == 6) {
			documentoParticipante.setId(null);
			documentoParticipanteService.guardar(documentoParticipante.getDocumento());
			documentoParticipanteService.guardar(documentoParticipante);
			
			result.setResultado("ok", "S");
			mensajes.mensaje(TiposMensaje.success, "Documento almacenado correctamente.");
		} else if (resultado == null) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("Usuario o contraseña incorrectos."));
			result.setMensajes(mensajes.getMensajes());
		} else {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La solicitud debe estar pendiente incorporación documentación para poder eliminar el documento."));
			result.setMensajes(mensajes.getMensajes());
		}
		
		result.setMensajes(mensajes.getMensajes());
        return result;
    }
	
	@PostMapping(value = "/eliminarDocumentoParticipante", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax eliminarDocumentoParticipante(@ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		DocumentoParticipante documentoParticipante = documentoParticipanteService.obtenerPorId(DocumentoParticipante.class, valores.getId());
		
		Actividad actividad = documentoParticipante.getParticipante().getActividad();
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		Participante p = new Participante();
		p.setActividad(documentoParticipante.getParticipante().getActividad());
		p.setEmail(valores.getNombre());
		p.setPassword(valores.getPassword());
		Participante resultado = actividadService.getParticipantePassword(p);
		
		if (resultado != null && resultado.getEstado().getId() == 6) {
			Integer idDocumento = documentoParticipante.getDocumento().getId();
			documentoParticipanteService.eliminar(DocumentoParticipante.class, documentoParticipante.getId());
			documentoParticipanteService.eliminar(Documento.class, idDocumento);
			
			result.setResultado("ok", "S");
			mensajes.mensaje(TiposMensaje.success, "Documento eliminado correctamente.");
		} else if (resultado == null) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("Usuario o contraseña incorrectos."));
			result.setMensajes(mensajes.getMensajes());
		} else {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La solicitud debe estar pendiente incorporación documentación para poder eliminar el documento."));
			result.setMensajes(mensajes.getMensajes());
		}
		
		
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/pasar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax pasar(@ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		Actividad actividad = new Actividad();
		actividad.setId(valores.getId());
		actividad.setEstado(new EstadosActividad());
		actividad.getEstado().setId(1);
		if(!permiso(actividad)) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La actividad no existe o está fuera de plazo"));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		Participante p = new Participante();
		p.setActividad(actividad);
		p.setEmail(valores.getNombre());
		p.setPassword(valores.getPassword());
		Participante resultado = actividadService.getParticipantePassword(p);
		
		if (resultado != null && resultado.getEstado().getId() == 6) {
			resultado.getEstado().setId(7);
			actividadService.guardar(resultado);
			
			result.setResultado("ok", "S");
			mensajes.mensaje(TiposMensaje.success, "Se ha solicitado la aceptación de la inscripción correctamente.");
		} else if (resultado == null) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("Usuario o contraseña incorrectos."));
			result.setMensajes(mensajes.getMensajes());
		} else {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("La solicitud debe estar pendiente incorporación documentación para poder eliminar el documento."));
			result.setMensajes(mensajes.getMensajes());
		}
		
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
//	private void getCaptcha(ValoresDTO valores) {
//		Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
//		valores.setHiddenCaptcha(captcha.getAnswer());
//		valores.setCaptcha(""); // value entered by the User
//		valores.setRealCaptcha("data:realCaptcha/jpg;base64," + CaptchaUtil.encodeCaptcha(captcha));
//		
//	}
}
