package es.arbox.eGestion.dto;

import java.util.ArrayList;
import java.util.List;

import es.arbox.eGestion.enums.TiposMensaje;

public class Mensajes{

	private List<Mensaje> datos;
	
	public Mensajes() {
		super();
		this.datos = new ArrayList<>();
	}

	public void mensaje(TiposMensaje tipo, String mensaje){
		datos.add(new Mensaje(tipo, mensaje));
	}
	
	public List<Mensaje> getMensajes(){
		return datos;
	}
}
