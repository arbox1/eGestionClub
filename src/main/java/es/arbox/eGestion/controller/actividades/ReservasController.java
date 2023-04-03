package es.arbox.eGestion.controller.actividades;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Pista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.reservas.ReservaService;

@Controller
@RequestMapping("/actividades/reservas")
public class ReservasController extends BaseController {
	
	@Autowired
	private ReservaService reservaService;

	@GetMapping("/")
	public String inicio(Model model) {
		
		model.addAttribute("pistas", reservaService.obtenerTodos(Pista.class));
		model.addAttribute("nuevo", new Reserva());
		
		return "/actividades/reservas";
	}
	
	@PostMapping(value = "/eliminar")
    public @ResponseBody RespuestaAjax eliminar(@ModelAttribute Reserva reserva, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		reservaService.eliminar(reserva.getClass(), reserva.getId());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Reserva eliminada correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Reserva reserva, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String msg = reserva.getId() != null ? "actualizada" : "creada";
		
		HorarioPista horario = reservaService.obtenerPorId(HorarioPista.class, Integer.valueOf(reserva.getHora()));
		
		Calendar c = Calendar.getInstance();
		c.setTime(reserva.getFecha());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), horario.getHora(), horario.getMinuto(), 0);
		reserva.setFecha(c.getTime());

		reservaService.guardar(reserva);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Reserva %1$s correctamente.", msg));
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
	
	@PostMapping(value = "/selectHora", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String selectGrupo(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		HorarioPista h = new HorarioPista();
		Pista pista = new Pista();
		pista.setId(valores.getId());
		h.setPista(pista);
		Calendar c = Calendar.getInstance();
		c.setTime(valores.getFechaDesde());
		h.setDia(c.get(Calendar.DAY_OF_WEEK)-1);
		h.setFechaDesde(valores.getFechaDesde());
		h.setFechaHasta(valores.getFechaDesde());
		
		List<HorarioPista> horarios = reservaService.getHorariosDisponibles(h);
		
		for(HorarioPista horario : horarios) {
			horario.setDescripcion(horario.getDescripcion());
		}
		
		result.setResultado("datos", Grupo.getListaMapa(horarios));
		
		return reservaService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Reserva reserva = new Reserva();
		reserva.setFechaDesde(valores.getFechaDesde());
		reserva.setFechaHasta(valores.getFechaHasta());
		
		List<Reserva> reservas = reservaService.getReservas(reserva);
		
		result.setResultado("reservas", reservas);
		
		return reservaService.serializa(result);
	}
	
	@SuppressWarnings({"deprecation" })
	@ResponseBody
	@PostMapping(value = "/cargarReserva", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargarReserva(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		DateFormat horaFormat = new SimpleDateFormat("HH");
		DateFormat minutoFormat = new SimpleDateFormat("mm");
		
		RespuestaAjax result = new RespuestaAjax();
		
		Reserva reserva = reservaService.obtenerPorId(Reserva.class, valores.getId());
		
		if(reserva != null) {
			HorarioPista h = new HorarioPista();
			Pista p = new Pista();

			p.setId(reserva.getPista().getId());
			h.setPista(p);
			h.setHora(Integer.valueOf(horaFormat.format(reserva.getFecha())));
			h.setMinuto(Integer.valueOf(minutoFormat.format(reserva.getFecha())));
			h.setDia(reserva.getFecha().getDay());
			List<HorarioPista> lHorarios = reservaService.getHorarios(h);
			if(lHorarios != null && lHorarios.size()>0)
				reserva.setHora(lHorarios.get(0).getId());	
		}
		
		result.setResultado("reserva", reserva);
		
		return reservaService.serializa(result);
	}
}
