package es.arbox.eGestion.service.reservas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.reservas.ReservaDAO;
import es.arbox.eGestion.entity.reservas.BloqueoReserva;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.entity.reservas.UsuarioReserva;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class ReservaServiceImpl extends GenericServiceImpl implements ReservaService {
	
	@Autowired
	private ReservaDAO reservaDAO;
	
	@Override
	@Transactional
	public UsuarioReserva getUsuarioReserva(String email) {
		return reservaDAO.getUsuarioReserva(email);
	}

	@Override
	@Transactional
	public List<BloqueoReserva> getBloqueos(BloqueoReserva br) {
		return reservaDAO.getBloqueos(br);
	}
	
	@Override
	@Transactional
	public List<HorarioPista> getHorarios(HorarioPista h){
		return reservaDAO.getHorarios(h);
	}
	
	@Override
	@Transactional
	public List<HorarioPista> getHorariosDisponibles(HorarioPista h) {
		Integer desfase = -1*h.getHora();
		h.setHora(null);
		
		LocalDateTime fechaMaxima = LocalDateTime.now().plusDays(15);
		
		LocalDateTime fechaReserva = LocalDateTime.ofInstant(h.getFechaDesde().toInstant(),
                ZoneId.systemDefault());
		
		if(fechaReserva.isAfter(fechaMaxima)) {
			return new ArrayList<>();
		}
		
		DateFormat horaFormat = new SimpleDateFormat("HH");
		DateFormat minutoFormat = new SimpleDateFormat("mm");
		
		List<HorarioPista> horariosPista = reservaDAO.getHorarios(h);
		
		List<HorarioPista> horariosEliminar = new ArrayList<>();
		for(HorarioPista horario : horariosPista) {
			Date fechaActual = new Date();
			
			Calendar c = Calendar.getInstance();
			c.setTime(h.getFechaDesde());
			c.set(Calendar.MILLISECOND, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MINUTE, horario.getMinuto());
	        c.set(Calendar.HOUR, horario.getHora());
	        c.add(Calendar.HOUR, desfase);
			if(fechaActual.after(c.getTime())) {
				horariosEliminar.add(horario);
			} else {
				Reserva r = new Reserva();
				r.setPista(h.getPista());
				r.setFecha(h.getFechaDesde());
				List<Reserva> reservas = reservaDAO.getReservas(r);
				for(Reserva reserva : reservas) {
					Integer hora = Integer.valueOf(horaFormat.format(reserva.getFecha()));
					Integer minuto = Integer.valueOf(minutoFormat.format(reserva.getFecha()));
					if(horario.getHora().equals(hora) && horario.getMinuto().equals(minuto)) {
						horariosEliminar.add(horario);
					}
				}
			}
			
			
			HorarioPista horarioDisponible = new HorarioPista();
			horarioDisponible.setPista(h.getPista());
			c = Calendar.getInstance();
			c.setTime(h.getFechaDesde());
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), horario.getHora(), horario.getMinuto(), 0);
			horarioDisponible.setFechaDesde(c.getTime());
			
			if(reservaDAO.fechaBloqueada(horarioDisponible)) {
				horariosEliminar.add(horario);
			};
		}
		
		horariosPista.removeAll(horariosEliminar);
			
		return horariosPista;
	}
	
	@Override
	@Transactional
	public List<Reserva> getReservas(Reserva reserva) {
		return reservaDAO.getReservas(reserva);
	}
}
