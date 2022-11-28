package es.arbox.eGestion.entity.ligas;

import java.util.Date;
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

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "calendario_liga")
public class CalendarioLiga extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cl_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cl_jl_id")
	protected Jornada jornada;
	
	@ManyToOne
	@JoinColumn(name = "cl_e_id_a")
	protected Equipo equipoa;
	
	@ManyToOne
	@JoinColumn(name = "cl_e_id_b")
	protected Equipo equipob;
	
	@Column(name = "cl_no_presentadoa")
	protected String noPresentadoa;
	
	@Column(name = "cl_no_presentadob")
	protected String noPresentadob;
	
	@Column(name ="cl_comentario")
	protected String comentario;
	
	@Column(name = "cl_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "cl_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "cl_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "cl_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	protected List<Resultado> resultados;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public Equipo getEquipoa() {
		return equipoa;
	}

	public void setEquipoa(Equipo equipoa) {
		this.equipoa = equipoa;
	}

	public Equipo getEquipob() {
		return equipob;
	}

	public void setEquipob(Equipo equipob) {
		this.equipob = equipob;
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

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public String getNoPresentadoa() {
		return noPresentadoa;
	}

	public void setNoPresentadoa(String noPresentadoa) {
		this.noPresentadoa = noPresentadoa;
	}

	public String getNoPresentadob() {
		return noPresentadob;
	}

	public void setNoPresentadob(String noPresentadob) {
		this.noPresentadob = noPresentadob;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
