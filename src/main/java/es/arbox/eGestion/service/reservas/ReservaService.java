package es.arbox.eGestion.service.reservas;

import java.util.List;

import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.service.config.GenericService;

public interface ReservaService extends GenericService {
	
	public List<HorarioPista> getHorarios(HorarioPista h);
	
	public List<HorarioPista> getHorariosDisponibles(HorarioPista h);
	
	public List<Reserva> getReservas(Reserva reserva);
}
