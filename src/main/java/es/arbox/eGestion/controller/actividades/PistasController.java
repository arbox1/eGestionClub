package es.arbox.eGestion.controller.actividades;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.mantenimiento.MantenimientoController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Pista;
import es.arbox.eGestion.entity.reservas.Sede;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.reservas.ReservaService;

@Controller
@RequestMapping("/actividades/pistas")
public class PistasController extends MantenimientoController<Pista>{
	
	@Autowired
	private ReservaService reservaService;

	@Override
	public String getReturn() {
		return "/actividades/pistas";
	}

	@Override
	public Pista getDato() {
		return new Pista();
	}

	@Override
	public Map<String, Object> addModel() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("sedes", menuService.obtenerTodos(Sede.class));
		return mapa;
	}
	
	@PostMapping(value = "/cargarHorarios", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String cargarHorarios(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();

		HorarioPista h = new HorarioPista();
		Pista pista = new Pista();
		pista.setId(valores.getId());
		h.setPista(pista);
		List<HorarioPista> horarios = reservaService.getHorarios(h);
		
		result.setResultado("horarios", Participante.getListaMapa(horarios));
		
		return reservaService.serializa(result);
	}
	
	@PostMapping(value = "/eliminarHorario")
    public @ResponseBody RespuestaAjax eliminarHorario(@ModelAttribute HorarioPista hoarario, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		reservaService.eliminar(HorarioPista.class, hoarario.getId());;
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Horario eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@ResponseBody
	@PostMapping(value = "/editarHorario", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String editarHorario(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		HorarioPista horario = reservaService.obtenerPorId(HorarioPista.class, valores.getId());
		
		result.setResultado("horario", horario.getMapa());
		
		return reservaService.serializa(result);
	}
	
	@PostMapping(value = "/guardarHorario")
    public @ResponseBody RespuestaAjax guardarHorario(@ModelAttribute HorarioPista horario, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = horario.getId() != null ? "actualizado" : "inscrito";
		
		reservaService.guardar(horario, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Horario %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/copiarHorario")
    public @ResponseBody RespuestaAjax copiarHorario(@ModelAttribute HorarioPista horario, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		HorarioPista copia = new HorarioPista();
		copia.setPista(horario.getPista());
		copia.setDia(horario.getHora());
		horario.setHora(null);
		
		for(HorarioPista h : reservaService.getHorarios(copia)) {
			reservaService.eliminar(HorarioPista.class, h.getId());
		}
		
		for(HorarioPista h : reservaService.getHorarios(horario)) {
			h.setId(null);
			h.setDia(copia.getDia());
			reservaService.guardar(h, getUsuarioLogado());
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Día copiada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/copiarPista")
    public @ResponseBody RespuestaAjax copiarPista(@ModelAttribute HorarioPista horario, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		HorarioPista copia = new HorarioPista();
		Pista pista = new Pista();
		pista.setId(horario.getDia());
		copia.setPista(pista);
		horario.setDia(null);
		
		for(HorarioPista h : reservaService.getHorarios(copia)) {
			reservaService.eliminar(HorarioPista.class, h.getId());
		}
		
		for(HorarioPista h : reservaService.getHorarios(horario)) {
			h.setId(null);
			h.setPista(copia.getPista());
			reservaService.guardar(h, getUsuarioLogado());
		}
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Pista copiada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
}
