
package es.arbox.eGestion.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "Usuarios")
public class Usuario extends BaseEntidad{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_id")
	private Integer id;
	
	@Column(name = "us_identificador")
	private String identificador;
	
	@Column(name = "us_password")
	private String password;
	
	@Column(name = "us_nombre")
	private String nombre;
	
	@Column(name = "us_apellido1")
	private String apellido1;
	
	@Column(name = "us_apellido2")
	private String apellido2;
	
	@Column(name = "us_correo")
	private String correo;
	
	@Column(name = "us_direccion")
	private String direccion;
	
	@Column(name = "us_telefono")
	private String telefono;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getApellidos() {
		return (apellido1.isEmpty() ? "" : apellido1) + " " + (apellido2.isEmpty() ? "" : apellido2);
	}
	
	public String getNombreCompleto() {
		return (nombre.isEmpty() ? "" : nombre) + " " + (apellido1.isEmpty() ? "" : apellido1) + " " + (apellido2.isEmpty() ? "" : apellido2);
	}
}
