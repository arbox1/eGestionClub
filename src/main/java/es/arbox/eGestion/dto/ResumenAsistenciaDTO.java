package es.arbox.eGestion.dto;

import java.util.HashMap;
import java.util.Map;

import es.arbox.eGestion.entity.config.Usuario;

public class ResumenAsistenciaDTO {

	public Usuario monitor;
	public Double total;
	public Map<Integer, AsistenciaMes> asistencias = new HashMap<>();
	
	public Usuario getMonitor() {
		return monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
	}

	public Map<Integer, AsistenciaMes> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(Map<Integer, AsistenciaMes> asistencias) {
		this.asistencias = asistencias;
	}

	public AsistenciaMes asistencia(Integer mes) {
		return asistencias.get(mes) != null ? asistencias.get(mes) : new AsistenciaMes();
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public static class AsistenciaMes {
		public String mes;
		public Integer anyo;
		public Double importe;
		public String getMes() {
			return mes;
		}
		public void setMes(String mes) {
			this.mes = mes;
		}
		public Double getImporte() {
			return importe;
		}
		public void setImporte(Double importe) {
			this.importe = importe;
		}
		public Integer getAnyo() {
			return anyo;
		}
		public void setAnyo(Integer anyo) {
			this.anyo = anyo;
		}
	}
}
