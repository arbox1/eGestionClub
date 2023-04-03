package es.arbox.eGestion.entity.reservas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "pista")
public class Pista extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "p_s_id")
	protected Sede sede;

	@Column(name = "p_descripcion")
	protected String descripcion;
	
	@Column(name = "p_observaciones")
	protected String observaciones;
	
	@Column(name = "p_color")
	protected String color;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
