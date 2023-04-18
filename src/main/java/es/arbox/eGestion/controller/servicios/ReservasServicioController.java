package es.arbox.eGestion.controller.servicios;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
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
import es.arbox.eGestion.entity.reservas.UsuarioReserva;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.service.reservas.ReservaService;
import es.arbox.eGestion.utils.PasswordGenerator;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/servicios/reservasServicio")
public class ReservasServicioController extends BaseController {
	
	@Autowired
	private ReservaService reservaService;
	
	@Autowired
	MailService mailService;

	@GetMapping("/")
	public String inicio(Model model) {
		
		model.addAttribute("pistas", reservaService.obtenerTodos(Pista.class));
		model.addAttribute("nuevo", new Reserva());
		
		return "/servicios/reservasServicio";
	}
	
	@PostMapping(value = "/guardar")
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Reserva reserva, @ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		if(!valores.getHiddenCaptcha().equals(valores.getCaptcha())) {
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("El captcha introducido no es correcto."));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		HorarioPista horario = reservaService.obtenerPorId(HorarioPista.class, Integer.valueOf(reserva.getHora()));
		
		reserva.setId(null);
		Calendar c = Calendar.getInstance();
		c.setTime(reserva.getFecha());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), horario.getHora(), horario.getMinuto(), 0);
		reserva.setFecha(c.getTime());
		
		String password = PasswordGenerator.getPassword(15);
		reserva.setHash(Utilidades.getMd5(password));
		
		reserva.setFechaCreacion(new Date());

		reservaService.guardar(reserva);
		
		UsuarioReserva usuarioReserva = reservaService.getUsuarioReserva(reserva.getEmail());
		usuarioReserva.setEmail(reserva.getEmail());
		usuarioReserva.setNombre(reserva.getNombre());
		usuarioReserva.setTelefono(reserva.getTelefono());
		reservaService.guardar(usuarioReserva);
		
		mailService.correoNuevaReserva(reservaService.obtenerPorId(Reserva.class, reserva.getId()), password);
		
		result.setResultado("ok", "S");
		mensajes.mensaje(TiposMensaje.success, String.format("Reserva creada correctamente."));
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
}
