package es.arbox.eGestion.entity.economica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "tarifas")
public class Tarifa extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "t_id")
	private Integer id;
	
	@Column(name = "t_descripcion")
	private String descripcion;
	
	@Column(name = "t_importe")
	private Double importe;

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

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
}
