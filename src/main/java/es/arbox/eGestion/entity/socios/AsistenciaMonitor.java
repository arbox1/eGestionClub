package es.arbox.eGestion.entity.socios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.converter.StringDateConverter;
import es.arbox.eGestion.entity.BaseEntidad;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.economica.Tarifa;

@Entity
@Table(name = "asistencia_monitor")
public class AsistenciaMonitor extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="am_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "am_u_id")
	protected Usuario monitor;

	@ManyToOne
	@JoinColumn(name = "am_t_id")
	protected Tarifa tarifa;
	
	@ManyToOne
	@JoinColumn(name = "am_e_id")
	protected Escuela escuela;
	
	@Column(name = "am_fecha")
	@JsonDeserialize(using = StringDateConverter.class, as = Date.class)
	protected Date fecha;
	
	@Column(name = "am_horas")
	protected Double horas;
	
	@Column(name = "am_observaciones")
	protected String observaciones;
	
	@Column(name = "am_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "am_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "am_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "am_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	protected Date fechaDesde;
	
	@Transient
	protected Date fechaHasta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getMonitor() {
		return monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public Escuela getEscuela() {
		return escuela;
	}

	public void setEscuela(Escuela escuela) {
		this.escuela = escuela;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getHoras() {
		return horas;
	}

	public void setHoras(Double horas) {
		this.horas = horas;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}

	public void setIdUsuarioCreacion(Integer idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdUsuarioActualizacion() {
		return idUsuarioActualizacion;
	}

	public void setIdUsuarioActualizacion(Integer idUsuarioActualizacion) {
		this.idUsuarioActualizacion = idUsuarioActualizacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}
