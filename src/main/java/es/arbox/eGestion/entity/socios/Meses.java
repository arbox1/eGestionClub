package es.arbox.eGestion.entity.socios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="meses",
				query="SELECT m FROM Meses m WHERE m.orden IS NOT NULL order by m.orden "
			)
})

@Entity
@Table(name = "meses")
public class Meses extends BaseEntidad{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="m_id")
	protected Integer id;
	
	@Column(name="m_descripcion")
	protected String descripcion;
	
	@Column(name="m_activo")
	protected String activo;
	
	@Column(name="m_orden")
	protected Integer orden;
	
	@Column(name="m_codigo")
	protected String codigo;
	
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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}