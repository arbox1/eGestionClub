package es.arbox.eGestion.entity.actividades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;
import es.arbox.eGestion.enums.EstadosParticipantes;

@Entity
@Table(name = "estados_participante")
public class EstadosParticipante extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ep_id")
	private Integer id;
	
	@Column(name = "ep_descripcion")
	private String descripcion;

	public EstadosParticipante() {
		super();
	}
	
	public EstadosParticipante(EstadosParticipantes estado) {
		this.id = estado.getId();
	}

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

}
