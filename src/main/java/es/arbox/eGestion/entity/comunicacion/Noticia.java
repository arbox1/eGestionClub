package es.arbox.eGestion.entity.comunicacion;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.arbox.eGestion.converter.StringDateConverter;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "noticia")
public class Noticia extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Integer id;
	
	@Column(name = "n_asunto")
	private String asunto;

	@Column(name = "n_descripcion")
	private String descripcion;
	
	@Column(name = "n_fecha")
	@JsonDeserialize(using = StringDateConverter.class, as = Date.class)
	private Date fecha;
	
	@Column(name = "n_visible")
	private String visible;
	
	@Transient
	private String hora;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
