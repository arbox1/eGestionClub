package es.arbox.eGestion.entity.documento;

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
			name="tipo_documento_familia",
				query="SELECT d FROM TipoDocumento d WHERE d.familia.descripcion = :familia order by d.id "
			)
}) 

@Entity
@Table(name = "tipos_documentos")
public class TipoDocumento extends BaseEntidad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="td_id")
	protected Integer id;
	
	@Column(name="td_codigo")
	protected String codigo;
	
	@Column(name="td_descripcion")
	protected String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "td_fd_id")
	protected FamiliaDocumento familia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public FamiliaDocumento getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaDocumento familia) {
		this.familia = familia;
	}

}
