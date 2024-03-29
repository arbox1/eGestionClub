package es.arbox.eGestion.entity.ligas;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.dto.PuestoDTO;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "grupo")
public class Grupo extends BaseEntidad{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "g_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "g_l_id")
	protected Liga liga;
	
	@Column(name = "g_descripcion")
	protected String descripcion;
	
	@Column(name = "g_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "g_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "g_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "g_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	protected List<Jornada> jornadas;
	
	@Transient
	protected List<Equipo> equipos;
	
	@Transient
	protected List<PuestoDTO> puestos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public List<Jornada> getJornadas() {
		return jornadas;
	}

	public void setJornadas(List<Jornada> jornadas) {
		this.jornadas = jornadas;
	}

	public List<PuestoDTO> getPuestos() {
		return puestos;
	}

	public void setPuestos(List<PuestoDTO> puestos) {
		this.puestos = puestos;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
}
