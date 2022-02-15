package es.arbox.eGestion.entity.socios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.converter.StringDoubleConverter;
import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
		@NamedQuery(name = "cuotas", query = "SELECT cu FROM Cuota cu where cu.socioCurso.id = :idSocioCurso order by cu.mes.orden asc ")
})

@Entity
@Table(name = "cuotas")
public class Cuota extends BaseEntidad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cu_id")
	protected Integer id;

	@ManyToOne
	@JoinColumn(name = "cu_sc_id")
	protected SociosCurso socioCurso;

	@ManyToOne
	@JoinColumn(name = "cu_m_id")
	protected Meses mes;

	@Column(name = "cu_importe")
	@JsonDeserialize(using = StringDoubleConverter.class, as = Double.class)
	protected Double importe;

	@Column(name = "cu_observaciones")
	protected String observaciones;

	@Column(name = "cu_fecha")
	protected Date fecha;

	@Column(name = "cu_notificado")
	protected String notificado;
	
	@Column(name = "cu_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "cu_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "cu_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "cu_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SociosCurso getSocioCurso() {
		return socioCurso;
	}

	public void setSocioCurso(SociosCurso socioCurso) {
		this.socioCurso = socioCurso;
	}

	public Meses getMes() {
		return mes;
	}

	public void setMes(Meses mes) {
		this.mes = mes;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNotificado() {
		return notificado;
	}

	public void setNotificado(String notificado) {
		this.notificado = notificado;
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
