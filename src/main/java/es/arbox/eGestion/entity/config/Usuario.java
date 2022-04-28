
package es.arbox.eGestion.entity.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="usuarioPorUsername",
				query="SELECT u FROM Usuario u WHERE u.username = :username "
			),
}) 

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuarios")
public class Usuario extends BaseEntidad implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_id")
	private Integer id;
	
	@Column(name = "us_identificador")
	private String username;
	
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
	
	@Column(name = "us_intento")
	private Integer intento;
	
	@Column(name = "us_f_baja")
	private Date fechaBaja;

	@Column(name = "us_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name = "us_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name = "us_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name = "us_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	private Set<Rol> roles;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	@JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> r = new ArrayList<>();
        for(Rol rol : roles) {
        	r.add(new SimpleGrantedAuthority(rol.getCodigo()));
        }
//        r.add(new SimpleGrantedAuthority("USER"));
        return r;
    }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public Integer getIntento() {
		return intento;
	}

	public void setIntento(Integer intento) {
		this.intento = intento;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
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
}
