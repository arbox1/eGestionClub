package es.arbox.eGestion.enums;

public enum EstadosParticipantes {
	SOLICITADA(1),
	ACEPTADA(2),
	RECHAZADA(3),
	CANCELADA(4),
	EN_RESERVA(5);
	
	Integer id;
	
	EstadosParticipantes(Integer id){
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}
