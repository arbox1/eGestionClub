package es.arbox.eGestion.dao.reservas;

import java.util.List;

import es.arbox.eGestion.entity.reservas.BloqueoReserva;
import es.arbox.eGestion.entity.reservas.HorarioPista;
import es.arbox.eGestion.entity.reservas.Reserva;
import es.arbox.eGestion.entity.reservas.UsuarioReserva;

public interface ReservaDAO {
	
	public UsuarioReserva getUsuarioReserva(String email);
	
	public boolean fechaBloqueada(HorarioPista h);
	
	public List<BloqueoReserva> getBloqueos(BloqueoReserva br);

	public List<HorarioPista> getHorarios(HorarioPista h);
	
	public List<Reserva> getReservas(Reserva reserva);
}
