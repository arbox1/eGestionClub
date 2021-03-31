package es.arbox.eGestion.entity.socios;

import java.util.Date;

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
			name="socios",
				query="SELECT s FROM Socios s order by s.apellidos, s.nombre "
			),
}) 

@Entity
@Table(name = "socios")
public class Socios extends BaseEntidad{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="so_id")
	protected Integer id;
	
	@Column(name="so_nombre")
	protected String nombre;
	
	@Column(name="so_apellidos")
	protected String apellidos;
	
	@Column(name="so_fecha_nacimiento")
	protected Date fechaNacimiento;
	
	@Column(name="so_telefono")
	protected String telefono;
	
	@Column(name="so_padre")
	protected String padre;
	
	@Column(name="so_telefono_padre")
	protected String telefonoPadre;
	
	@Column(name="so_madre")
	protected String madre;
	
	@Column(name="so_telefono_madre")
	protected String telefonoMadre;
	
	@Column(name="so_email")
	protected String email;
	
	@ManyToOne
	@JoinColumn(name = "so_s_id")
	protected Sexo sexo;
	
	@Column(name="so_lopd")
	protected Integer lopd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public String getTelefonoPadre() {
		return telefonoPadre;
	}

	public void setTelefonoPadre(String telefonoPadre) {
		this.telefonoPadre = telefonoPadre;
	}

	public String getMadre() {
		return madre;
	}

	public void setMadre(String madre) {
		this.madre = madre;
	}

	public String getTelefonoMadre() {
		return telefonoMadre;
	}

	public void setTelefonoMadre(String telefonoMadre) {
		this.telefonoMadre = telefonoMadre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Integer getLopd() {
		return lopd;
	}

	public void setLopd(Integer lopd) {
		this.lopd = lopd;
	}
	
	public String getNombreCompleto() {
		return apellidos + ", " + nombre;
	}
}
