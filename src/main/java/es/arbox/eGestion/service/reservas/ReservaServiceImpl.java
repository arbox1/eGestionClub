package es.arbox.eGestion.service.reservas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.dao.reservas.ReservaDAO;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class ReservaServiceImpl extends GenericServiceImpl implements ReservaService {
	
	@Autowired
	private ReservaDAO reservaDAO;

	@Override
	@Transactional
	public List<HorarioPista> getHorarios(HorarioPista h){
		return reservaDAO.getHorarios(h);
	}
	
	@Override
	@Transactional
	public List<HorarioPista> getHorariosDisponibles(HorarioPista h) {
		
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
	        c.add(Calendar.HOUR, -2);
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
