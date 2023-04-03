package es.arbox.eGestion.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.arbox.eGestion.entity.BaseEntidad;

public class ValoresDTO extends BaseEntidad {
	private Integer id;
	
	private String nombre;
	
	private String descripcion;
	
	private String password;
	
	private String captcha;
	
	private String hiddenCaptcha;
	
	private String realCaptcha;
	
	private Date fechaDesde;
	
	private Date fechaHasta;
	
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getHiddenCaptcha() {
		return hiddenCaptcha;
	}

	public void setHiddenCaptcha(String hiddenCaptcha) {
		this.hiddenCaptcha = hiddenCaptcha;
	}

	public String getRealCaptcha() {
		return realCaptcha;
	}

	public void setRealCaptcha(String realCaptcha) {
		this.realCaptcha = realCaptcha;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	public void setFechaDesdeString(String fechaDesde) throws ParseException {
		this.fechaDesde = format.parse(fechaDesde);
	}
	
	public String getFechaDesdeString() {
		return format.format(this.fechaDesde);
	}
}
