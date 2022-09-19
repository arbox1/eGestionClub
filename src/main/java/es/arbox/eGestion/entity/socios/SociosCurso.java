package es.arbox.eGestion.entity.socios;

import java.util.Date;
import java.util.Map;

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
import javax.persistence.Transient;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="socios_curso",
				query="SELECT sc FROM SociosCurso sc where sc.socio.id = :idSocio order by sc.curso.id desc, sc.escuela.descripcion asc, sc.categoria.id asc "
			),
	@NamedQuery(
			name="socios_filtro",
				query="SELECT sc "
				    + "  FROM SociosCurso sc "
					+ " WHERE sc.curso.id = :idCurso "
					+ "   AND (sc.socio.id = :idSocio OR -1 = :idSocio)"
					+ "   AND (sc.escuela.id = :idEscuela OR -1 = :idEscuela)"
					+ "   AND (sc.categoria.id = :idCategoria OR -1 = :idCategoria) "
					+ "ORDER BY sc.socio.apellidos, sc.socio.nombre "
			),
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
	
	@Column(name = "sc_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "sc_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "sc_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "sc_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	protected Map<Integer, Cuota> cuotas;

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

	public Integer getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}

	public void setIdUsuarioCreacion(Integer idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdUsuarioActualizacion() {
		return idUsuarioActualizacion;
	}

	public void setIdUsuarioActualizacion(Integer idUsuarioActualizacion) {
		this.idUsuarioActualizacion = idUsuarioActualizacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Map<Integer, Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(Map<Integer, Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	
	public Cuota cuota(Integer mes) {
		return cuotas.get(mes) != null ? cuotas.get(mes) : new Cuota();
	}
	
	public Double getTotal() {
		Double total = (double) 0;
		for(Integer key : cuotas.keySet()) {
			total += cuotas.get(key).importe;
		}
		return total;
	}
}
