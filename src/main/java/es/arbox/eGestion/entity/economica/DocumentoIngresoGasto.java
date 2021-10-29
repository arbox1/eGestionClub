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
import es.arbox.eGestion.entity.documento.Documento;

@NamedQueries({
	@NamedQuery(
			name="documentos_ingreso_gasto",
				query="SELECT d FROM DocumentoIngresoGasto d WHERE d.ingresoGasto.id = :idIngresoGasto order by d.documento.tipo.id, d.documento.id "
			)
}) 

@Entity
@Table(name = "documentos_ingreso_gasto")
public class DocumentoIngresoGasto extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dig_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "dig_doc_id")
	protected Documento documento;
	
	@ManyToOne
	@JoinColumn(name = "dig_ig_id")
	protected IngresosGastos ingresoGasto;

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

	public IngresosGastos getIngresoGasto() {
		return ingresoGasto;
	}

	public void setIngresoGasto(IngresosGastos ingresoGasto) {
		this.ingresoGasto = ingresoGasto;
	}	
}
