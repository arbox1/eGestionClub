package es.arbox.eGestion.entity.documento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "familia_documento")
public class FamiliaDocumento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fd_id")
	protected Integer id;
	
	@Column(name="fd_descripcion")
	protected String descripcion;

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
