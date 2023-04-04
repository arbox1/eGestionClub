package es.arbox.eGestion.entity.reservas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.arbox.eGestion.converter.StringDateConverter;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "reserva")
public class Reserva extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "r_p_id")
	protected Pista pista;

	@Column(name = "r_fecha")
	@JsonDeserialize(using = StringDateConverter.class, as = Date.class)
	protected Date fecha;
	
	@Column(name = "r_email")
	protected String email;
	
	@Column(name = "r_nombre")
	protected String nombre;
	
	@Column(name = "r_telefono")
	protected String telefono;
	
	@Column(name = "r_hash")
	protected String hash;
	
	@Column(name = "r_fecha_creacion")
	protected Date fechaCreacion;
	
	@Transient	
	protected Integer hora;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pista getPista() {
		return pista;
	}

	public void setPista(Pista pista) {
		this.pista = pista;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
