package es.arbox.eGestion.entity.actividades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "tipos_actividad")
public class TiposActividad extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ta_id")
	private Integer id;
	
	@Column(name = "ta_descripcion")
	private String descripcion;
	
	@Column(name = "ta_activo")
	private String activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
}
