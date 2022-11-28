package es.arbox.eGestion.entity.ligas;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "jornada_liga")
public class Jornada extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jl_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "jl_g_id")
	protected Grupo grupo;
	
	@Column(name = "jl_numero")
	protected Integer numero;
	
	@Transient
	protected List<CalendarioLiga> calendarios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public List<CalendarioLiga> getCalendarios() {
		return calendarios;
	}

	public void setCalendarios(List<CalendarioLiga> calendarios) {
		this.calendarios = calendarios;
	}
}
