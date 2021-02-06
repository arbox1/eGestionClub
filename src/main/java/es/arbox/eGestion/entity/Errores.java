package es.arbox.eGestion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "errores")
public class Errores {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "e_id")
	private int id;
	
	@Column(name = "e_error")
	private String error;
	
	@Column(name = "e_fecha")
	private Date fecha;
	
	public Errores(String error) {
		super();
		this.error = error;
		this.fecha = new Date();
	}

	public Errores(String error, Date fecha) {
		super();
		this.error = error;
		this.fecha = fecha;
	}

	public Errores() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
