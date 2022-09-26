package es.arbox.eGestion.entity.ligas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;
import es.arbox.eGestion.entity.actividades.EstadosActividad;
import es.arbox.eGestion.entity.actividades.TiposActividad;

@Entity
@Table(name = "liga")
public class Liga extends BaseEntidad{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "l_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "l_ta_id")
	protected TiposActividad tipo;
	
	@ManyToOne
	@JoinColumn(name = "l_e_id")
	protected EstadosActividad estado;
	
	@Column(name = "l_descripcion")
	protected String descripcion;
	
	@Column(name = "l_lugar")
	protected String lugar;
	
	@Column(name = "l_set")
	protected Integer set;
	
	@Column(name = "l_puntos_partido_ganado")
	protected Integer puntosPartidoGanado;
	
	@Column(name = "l_puntos_partido_perdido")
	protected Integer puntosPartidoPerdido;

	@Column(name = "l_puntos_partido_empatado")
	protected Integer puntosPartidoEmpatado;

	@Column(name = "l_puntos_partido_no_presentado")
	protected Integer puntosPartidoNoPresentado;
	
	@Column(name = "l_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "l_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "l_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "l_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TiposActividad getTipo() {
		return tipo;
	}

	public void setTipo(TiposActividad tipo) {
		this.tipo = tipo;
	}

	public EstadosActividad getEstado() {
		return estado;
	}

	public void setEstado(EstadosActividad estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Integer getSet() {
		return set;
	}

	public void setSet(Integer set) {
		this.set = set;
	}

	public Integer getPuntosPartidoGanado() {
		return puntosPartidoGanado;
	}

	public void setPuntosPartidoGanado(Integer puntosPartidoGanado) {
		this.puntosPartidoGanado = puntosPartidoGanado;
	}

	public Integer getPuntosPartidoPerdido() {
		return puntosPartidoPerdido;
	}

	public void setPuntosPartidoPerdido(Integer puntosPartidoPerdido) {
		this.puntosPartidoPerdido = puntosPartidoPerdido;
	}

	public Integer getPuntosPartidoEmpatado() {
		return puntosPartidoEmpatado;
	}

	public void setPuntosPartidoEmpatado(Integer puntosPartidoEmpatado) {
		this.puntosPartidoEmpatado = puntosPartidoEmpatado;
	}

	public Integer getPuntosPartidoNoPresentado() {
		return puntosPartidoNoPresentado;
	}

	public void setPuntosPartidoNoPresentado(Integer puntosPartidoNoPresentado) {
		this.puntosPartidoNoPresentado = puntosPartidoNoPresentado;
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
}
