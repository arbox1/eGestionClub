package es.arbox.eGestion.entity.reservas;

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

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "bloqueo_reserva")
public class BloqueoReserva extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "br_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "br_p_id")
	protected Pista pista;
	
	@Column(name = "br_f_desde")
	protected Date fechaDesde;
	
	@Column(name = "br_f_hasta")
	protected Date fechaHasta;
	
	@Column(name = "br_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "br_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "br_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "br_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	private Integer horaDesde;
	
	@Transient
	private Integer minutoDesde;
	
	@Transient
	private Integer horaHasta;
	
	@Transient
	private Integer minutoHasta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pista getPista() {
		return pista;
	}

	public void setPista(Pista pista) {
		this.pista = pista;
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

	public Integer getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(Integer horaDesde) {
		this.horaDesde = horaDesde;
	}

	public Integer getMinutoDesde() {
		return minutoDesde;
	}

	public void setMinutoDesde(Integer minutoDesde) {
		this.minutoDesde = minutoDesde;
	}

	public Integer getHoraHasta() {
		return horaHasta;
	}

	public void setHoraHasta(Integer horaHasta) {
		this.horaHasta = horaHasta;
	}

	public Integer getMinutoHasta() {
		return minutoHasta;
	}

	public void setMinutoHasta(Integer minutoHasta) {
		this.minutoHasta = minutoHasta;
	}
}
