package es.arbox.eGestion.controller.actividades;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.actividades.Actividad;
import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.entity.actividades.EstadosActividad;
import es.arbox.eGestion.entity.actividades.EstadosParticipante;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.actividades.TiposActividad;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposDocumento;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.actividades.ActividadService;
import es.arbox.eGestion.service.actividades.DocumentoActividadService;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/actividades/actividades")
public class ActividadesController extends BaseController {
	
	@Autowired
	ActividadService actividadService;
	
	@Autowired
	DocumentoActividadService documentoActividadService;
	
	@Autowired
	MailService mailService;
	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") Actividad actividad) {
		model.addAttribute("actividades", new ArrayList<Actividad>());
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("buscador", actividad == null ? new Actividad() : actividad);
		model.addAttribute("tiposDocumentos", documentoActividadService.getTipoDocumento(FamiliasDocumento.ACTIVIDAD));
		model.addAttribute("estadosActividad", actividadService.obtenerTodosOrden(EstadosActividad.class, " descripcion "));
		model.addAttribute("estadosParticipante", actividadService.obtenerTodosOrden(EstadosParticipante.class, " descripcion "));
		return "/actividades/actividades";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Actividad actividad, RedirectAttributes redirectAttrs) {
		model.addAttribute("tipos", actividadService.obtenerTodosOrden(TiposActividad.class, " descripcion "));
		model.addAttribute("tiposDocumentos", documentoActividadService.getTipoDocumento(FamiliasDocumento.ACTIVIDAD));
		model.addAttribute("estadosActividad", actividadService.obtenerTodosOrden(EstadosActividad.class, " descripcion "));
		model.addAttribute("estadosParticipante", actividadService.obtenerTodosOrden(EstadosParticipante.class, " descripcion "));
		
		List<Actividad> actividades = actividadService.getActividadesFiltro(actividad);
		for(Actividad act : actividades) {
			act.setInscritos(actividadService.getInscritos(act.getId()));
		}
		
		model.addAttribute("actividades", actividades);
		return "/actividades/actividades";
    }
	
	@PostMapping(value = "/notificarSalida")
    public @ResponseBody RespuestaAjax notificarSalida(@ModelAttribute Actividad actividad, RedirectAttributes redirectAttrs) throws JsonProcessingException, MessagingException {
		RespuestaAjax result = new RespuestaAjax();
		
		List<DocumentoActividad> documentos = documentoActividadService.getDocumentosPorTipo(actividad.getId(), TiposDocumento.ANTES_ACTIVIDAD);
		for(Participante participante : actividadService.getParticipantes(actividad.getId())) {
			mailService.correoActividadSalida(participante, documentos);
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Notificación realizada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/notificarLlegada")
    public @ResponseBody RespuestaAjax notificarLlegada(@ModelAttribute Actividad actividad, RedirectAttributes redirectAttrs) throws JsonProcessingException, MessagingException {
		RespuestaAjax result = new RespuestaAjax();
		
		List<DocumentoActividad> documentos = documentoActividadService.getDocumentosPorTipo(actividad.getId(), TiposDocumento.DESPUES_ACTIVIDAD);
		for(Participante participante : actividadService.getParticipantes(actividad.getId())) {
			mailService.correoActividadLlegada(participante, documentos);
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Notificación realizada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute Actividad actividad, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		actividadService.eliminar(Actividad.class, actividad.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Actividad eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Actividad actividad, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = actividad.getId() != null ? "actualizada" : "realizada";
		
		if(!StringUtils.isEmpty(actividad.getHoraInicio())) {
			actividad.setFechaInicio(Utilidades.asignarHora(actividad.getFechaInicio(), actividad.getHoraInicio()));
		}
		
		if(!StringUtils.isEmpty(actividad.getHoraFin())) {
			actividad.setFechaFin(Utilidades.asignarHora(actividad.getFechaFin(), actividad.getHoraFin()));
		}
		
		if(!StringUtils.isEmpty(actividad.getHoraFinPlazo())) {
			actividad.setFechaFinPlazo(Utilidades.asignarHora(actividad.getFechaFinPlazo(), actividad.getHoraFinPlazo()));
		}
		
		actividadService.guardar(actividad, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Actividad %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		Actividad actividad = actividadService.obtenerPorId(Actividad.class, valores.getId());		
		
		result.setResultado("actividad", actividad.getMapa());
		
		return actividadService.serializa(result);
    }
	
	@PostMapping(value = "/participantes", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String participantes(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<Participante> participantes = actividadService.getParticipantes(valores.getId());
		
		result.setResultado("participantes", Participante.getListaMapa(participantes));
		
		return actividadService.serializa(result);
	}
	
	@PostMapping(value = "/guardarParticipante")
    public @ResponseBody RespuestaAjax guardarParticipante(@ModelAttribute Participante participante, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = participante.getId() != null ? "actualizado" : "inscrito";
		
		if(participante.getId() == null) {
			participante.setFecha(new Date());
		} else {
			Participante p = actividadService.obtenerPorId(participante.getClass(), participante.getId());
			participante.setPassword(p.getPassword());
			participante.setFecha(p.getFecha());
			participante.setIdUsuarioCreacion(p.getIdUsuarioCreacion());
			participante.setFechaCreacion(p.getFechaCreacion());
		}
		
		actividadService.guardar(participante, getUsuarioLogado());
		
		result.setResultado("id", participante.getActividad().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Participante %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/guardarNotificarParticipante")
    public @ResponseBody RespuestaAjax guardarNotificarParticipante(@ModelAttribute Participante participante, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = guardarParticipante(participante, redirectAttrs);
		
		mailService.correoNotificacionParticipante(actividadService.obtenerPorId(participante.getClass(), participante.getId()));
		
		return result;
    }
	
	@PostMapping(value = "/eliminarParticipante")
    public @ResponseBody RespuestaAjax eliminarParticipante(@ModelAttribute Participante participante, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		actividadService.eliminar(Participante.class, participante.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Participante eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/participante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String participante(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Participante participante = actividadService.obtenerPorId(Participante.class, valores.getId());
		
		result.setResultado("participante", participante.getMapa());
		
		return actividadService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/documentos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String documentos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<DocumentoActividad> documentoActividad = documentoActividadService.getDocumentos(valores.getId());
		
		result.setResultado("documento", DocumentoActividad.getListaMapa(documentoActividad));
		
		return documentoActividadService.serializa(result);
	}
	
	@PostMapping(value = "/guardarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarDocumento(@ModelAttribute DocumentoActividad documentoActividad, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = documentoActividad.getId() != null ? "actualizado" : "realizado";
		
		documentoActividadService.guardar(documentoActividad.getDocumento(), getUsuarioLogado());
		documentoActividadService.guardar(documentoActividad);
		
		result.setResultado("id", documentoActividad.getActividad().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Documento %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax eliminarDocumento(@ModelAttribute DocumentoActividad documentoActividad, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		Integer idDocumento = documentoActividad.getDocumento().getId();

		documentoActividadService.eliminar(DocumentoActividad.class, documentoActividad.getId());
		documentoActividadService.eliminar(Documento.class, idDocumento);
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Documento eliminado correctamente.");
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
