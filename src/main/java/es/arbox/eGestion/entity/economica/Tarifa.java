package es.arbox.eGestion.entity.economica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

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
	
	@Column(name = "t_activo")
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

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	public String getActivoTexto() {
		return StringUtils.isNotBlank(activo) && activo.equals("S") ? "Si" : "No";
	}
}
