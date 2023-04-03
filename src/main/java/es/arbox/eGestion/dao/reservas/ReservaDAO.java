package es.arbox.eGestion.dao.reservas;

import java.util.List;

import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;

public interface ReservaDAO {

	public List<HorarioPista> getHorarios(HorarioPista h);
	
	public List<Reserva> getReservas(Reserva reserva);
}
