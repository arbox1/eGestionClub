package es.arbox.eGestion.entity.socios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "directiva")
public class Directiva extends BaseEntidad{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="d_id")
	protected Integer id;
	
	@Column(name="d_cargo")
	protected String cargo;
	
	@Column(name="d_nombre")
	protected String nombre;
	
	@Column(name="d_orden")
	protected Integer orden;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
