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

@NamedQueries({
	@NamedQuery(
			name="socios_curso",
				query="SELECT sc FROM SociosCurso sc where sc.socio.id = :idSocio order by sc.curso.id desc, sc.escuela.descripcion asc, sc.categoria.id asc "
			)
}) 

@Entity
@Table(name = "socios_curso")
public class SociosCurso extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sc_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "sc_cur_id")
	protected Curso curso;
	
	@ManyToOne
	@JoinColumn(name = "sc_so_id")
	protected Socios socio;
	
	@ManyToOne
	@JoinColumn(name = "sc_c_id")
	protected Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "sc_e_id")
	protected Escuela escuela;
	
	@ManyToOne
	@JoinColumn(name = "sc_m_entrada")
	protected Meses entrada;
	
	@ManyToOne
	@JoinColumn(name = "sc_m_salida")
	protected Meses salida;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Socios getSocio() {
		return socio;
	}

	public void setSocio(Socios socio) {
		this.socio = socio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Escuela getEscuela() {
		return escuela;
	}

	public void setEscuela(Escuela escuela) {
		this.escuela = escuela;
	}

	public Meses getEntrada() {
		return entrada;
	}

	public void setEntrada(Meses entrada) {
		this.entrada = entrada;
	}

	public Meses getSalida() {
		return salida;
	}

	public void setSalida(Meses salida) {
		this.salida = salida;
	}
}
