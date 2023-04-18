package es.arbox.eGestion.controller.servicios;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.config.MailService;
import es.arbox.eGestion.service.reservas.ReservaService;
import es.arbox.eGestion.utils.Utilidades;

@Controller
@RequestMapping("/servicios/anularReservaServicio")
public class AnularReservaServicioController extends BaseController {
	
	@Autowired
	private ReservaService reservaService;
	
	@Autowired
	MailService mailService;
	
	@GetMapping("/")
	public String inicio(@RequestParam String hash, Model model) {
		
		if(StringUtils.isNotBlank(hash)) {
			Reserva r = new Reserva();
			r.setHash(Utilidades.getMd5(hash));
			List<Reserva> reservas = reservaService.getReservas(r);
			for(Reserva reserva : reservas) {
				reserva.setHash(hash);
				model.addAttribute("nuevo", reserva);
			}
		}
		
		return "/servicios/anularReservaServicio";
	}
	
	@PostMapping(value = "/anular")
    public @ResponseBody RespuestaAjax anular(@ModelAttribute Reserva reserva, @ModelAttribute ValoresDTO valores, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		Mensajes mensajes = new Mensajes();
		
		if(!valores.getHiddenCaptcha().equals(valores.getCaptcha())) {
		
			result.setResultado("ok", "N");
			mensajes.mensaje(TiposMensaje.danger, String.format("El captcha introducido no es correcto."));
			result.setMensajes(mensajes.getMensajes());
			
			return result;
		}
		
		reserva.setHash(Utilidades.getMd5(reserva.getHash()));
		List<Reserva> reservas = reservaService.getReservas(reserva);
		for(Reserva r : reservas) {
			LocalDateTime fechaReserva = LocalDateTime.ofInstant(r.getFecha().toInstant(),
                    ZoneId.systemDefault());
			
			LocalDateTime fechaActual = LocalDateTime.now().minusHours(2);
			
			if(fechaActual.isBefore(fechaReserva)) {
				reservaService.eliminar(r.getClass(), r.getId());
				
				mailService.correoAnularReserva(reserva);
				
				result.setResultado("ok", "S");
				mensajes.mensaje(TiposMensaje.success, String.format("Reserva anulada correctamente."));
				result.setMensajes(mensajes.getMensajes());
			} else {
				result.setResultado("ok", "N");
				mensajes.mensaje(TiposMensaje.danger, String.format("La reserva no puede ser anulada ya que ha pasado el tiempo máximo de anulación."));
				result.setMensajes(mensajes.getMensajes());
			}
		}
		
		return result;
    }
}
