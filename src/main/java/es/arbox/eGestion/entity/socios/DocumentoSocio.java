package es.arbox.eGestion.entity.socios;

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
import es.arbox.eGestion.entity.documento.Documento;

@NamedQueries({
	@NamedQuery(
			name="documentos_socio",
				query="SELECT d FROM DocumentoSocio d WHERE d.socio.id = :idSocio order by d.documento.tipo.id, d.documento.id "
			),
}) 

@Entity
@Table(name = "documentos_socios")
public class DocumentoSocio extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ds_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ds_doc_id")
	protected Documento documento;
	
	@ManyToOne
	@JoinColumn(name = "ds_s_id")
	protected Socios socio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Socios getSocio() {
		return socio;
	}

	public void setSocio(Socios socio) {
		this.socio = socio;
	}
}
