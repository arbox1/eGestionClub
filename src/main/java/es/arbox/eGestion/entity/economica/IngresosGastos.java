package es.arbox.eGestion.entity.economica;

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

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "ingresos_gastos")
public class IngresosGastos extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ig_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ig_si_id")
	protected SubtiposImporte subTipo;
	
	@Column(name = "ig_descripcion")
	protected String descripcion;
	
	@Column(name = "ig_importe")
	protected Double importe;
	
	@Column(name = "ig_observaciones")
	protected String observaciones;
	
	@Column(name = "ig_fecha")
	protected Date fecha;
	
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

	public SubtiposImporte getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(SubtiposImporte subTipo) {
		this.subTipo = subTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
