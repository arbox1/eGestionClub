package es.arbox.eGestion.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQuery(
name="tipoMenu",
	query="SELECT tm FROM TiposMenu tm where id = :idTipo"//WHERE m.padre.id IS NULL "
)

@Entity
@Table(name = "tipos_menu")
public class TiposMenu extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_id")
	private int id;

	@Column(name = "tm_codigo")
	private String codigo;

	@Column(name = "tm_descripcion")
	private String descripcion;
	
	public TiposMenu() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
