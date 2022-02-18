package es.arbox.eGestion.entity.socios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="cursos",
				query="SELECT c FROM Curso c where c.activo = 'S' order by c.id desc "
			)
})

@Entity
@Table(name = "curso")
public class Curso extends BaseEntidad{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cur_id")
	protected Integer id;
	
	@Column(name="cur_descripcion")
	protected String descripcion;
	
	@Column(name="cur_activo")
	protected String activo;

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
	
	public String getActivoTexto() {
		return StringUtils.isNotBlank(activo) && activo.equals("S") ? "Si" : "No";
	}
}
