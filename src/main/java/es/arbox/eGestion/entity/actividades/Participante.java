package es.arbox.eGestion.entity.actividades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.arbox.eGestion.converter.StringDateConverter;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "participante")
public class Participante extends BaseEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "p_a_id")
	private Actividad actividad;
	
	@Column(name = "p_nombre")
	private String nombre;
	
	@Column(name = "p_observacion")
	private String observacion;
	
	@Column(name = "p_cantidad")
	private Integer cantidad;
	
	@Column(name = "p_importe")
	private Integer importe;
	
	@Column(name = "p_pagado")
	private String pagado;
	
	@Column(name = "p_fecha")
	@JsonDeserialize(using = StringDateConverter.class, as = Date.class)
	private Date fecha;
	
	@Column(name = "p_telefono")
	private String telefono;
	
	@Column(name = "p_email")
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	public String getPagado() {
		return pagado;
	}

	public void setPagado(String pagado) {
		this.pagado = pagado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
