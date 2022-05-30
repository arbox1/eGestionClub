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
			name="documentos_participante",
				query="SELECT d FROM DocumentoParticipante d WHERE d.participante.id = :idParticipante order by d.documento.tipo.id, d.documento.id "
			)
}) 

@Entity
@Table(name = "documentos_participante")
public class DocumentoParticipante extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dp_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "dp_doc_id")
	protected Documento documento;
	
	@ManyToOne
	@JoinColumn(name = "dp_p_id")
	protected Participante participante;

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

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
}
