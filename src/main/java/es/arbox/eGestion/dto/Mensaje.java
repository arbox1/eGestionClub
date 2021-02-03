package es.arbox.eGestion.dto;

import es.arbox.eGestion.enums.TiposMensaje;

public class Mensaje {
	private TiposMensaje tipo;
	private String mensaje;
	public TiposMensaje getTipo() {
		return tipo;
	}
	public void setTipo(TiposMensaje tipo) {
		this.tipo = tipo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Mensaje(TiposMensaje tipo, String mensaje) {
		super();
		this.tipo = tipo;
		this.mensaje = mensaje;
	}
}
