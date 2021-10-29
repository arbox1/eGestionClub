package es.arbox.eGestion.entity.actividades;

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
			name="documentos_actividad",
				query="SELECT d FROM DocumentoActividad d WHERE d.actividad.id = :idActividad order by d.documento.tipo.id, d.documento.id "
			)
}) 

@Entity
@Table(name = "documentos_actividad")
public class DocumentoActividad extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="da_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "da_doc_id")
	protected Documento documento;
	
	@ManyToOne
	@JoinColumn(name = "da_a_id")
	protected Actividad actividad;

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

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
}
