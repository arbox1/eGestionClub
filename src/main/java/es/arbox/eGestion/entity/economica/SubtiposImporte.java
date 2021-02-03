package es.arbox.eGestion.entity.economica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="subtiposImporte",
				query="SELECT m FROM SubtiposImporte m WHERE m.tipoImporte.id = :idTipo order by m.orden "
			),
}) 

@Entity
@Table(name = "subtipos_importe")
public class SubtiposImporte extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "si_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "si_ti_id")
	protected TiposImporte tipoImporte;

	@Column(name = "si_descripcion")
	protected String descripcion;
	
	@Column(name = "si_orden")
	protected Integer orden;

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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public TiposImporte getTipoImporte() {
		return tipoImporte;
	}

	public void setTipoImporte(TiposImporte tipoImporte) {
		this.tipoImporte = tipoImporte;
	}
	
	public String getDescripcionLarga() {
		return String.format("%2$s - %1$s", descripcion, tipoImporte.getDescripcion());
	}
}
