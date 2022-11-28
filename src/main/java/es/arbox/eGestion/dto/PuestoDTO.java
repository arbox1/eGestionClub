package es.arbox.eGestion.dto;

public class PuestoDTO {
	protected Integer posicion;
	protected Integer idEquipo;
	protected String equipo;
	protected Integer puntos = 0;
	protected Integer encuentrosJugados = 0;
	protected Integer encuentrosGanados = 0;
	protected Integer encuentrosEmpatados = 0;
	protected Integer encuentrosPerdidos = 0;
	protected Integer encuentrosNoPresentados = 0;
	protected Integer tantosFavor = 0;
	protected Integer tantosContra = 0;
	public Integer getPosicion() {
		return posicion;
	}
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}
	public Integer getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public Integer getEncuentrosJugados() {
		return encuentrosJugados;
	}
	public void setEncuentrosJugados(Integer encuentrosJugados) {
		this.encuentrosJugados = encuentrosJugados;
	}
	public Integer getEncuentrosGanados() {
		return encuentrosGanados;
	}
	public void setEncuentrosGanados(Integer encuentrosGanados) {
		this.encuentrosGanados = encuentrosGanados;
	}
	public Integer getEncuentrosEmpatados() {
		return encuentrosEmpatados;
	}
	public void setEncuentrosEmpatados(Integer encuentrosEmpatados) {
		this.encuentrosEmpatados = encuentrosEmpatados;
	}
	public Integer getEncuentrosPerdidos() {
		return encuentrosPerdidos;
	}
	public void setEncuentrosPerdidos(Integer encuentrosPerdidos) {
		this.encuentrosPerdidos = encuentrosPerdidos;
	}
	public Integer getEncuentrosNoPresentados() {
		return encuentrosNoPresentados;
	}
	public void setEncuentrosNoPresentados(Integer encuentrosNoPresentados) {
		this.encuentrosNoPresentados = encuentrosNoPresentados;
	}
	public Integer getTantosFavor() {
		return tantosFavor;
	}
	public void setTantosFavor(Integer tantosFavor) {
		this.tantosFavor = tantosFavor;
	}
	public Integer getTantosContra() {
		return tantosContra;
	}
	public void setTantosContra(Integer tantosContra) {
		this.tantosContra = tantosContra;
	}
}
