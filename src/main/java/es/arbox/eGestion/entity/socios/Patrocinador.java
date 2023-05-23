package es.arbox.eGestion.entity.socios;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import es.arbox.eGestion.annotations.Auditoria;
import es.arbox.eGestion.entity.BaseEntidad;

@Entity
@Table(name = "patrocinadores")
public class Patrocinador extends BaseEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="p_id")
	protected Integer id;
	
	@Column(name="p_descripcion")
	protected String descripcion;
	
	@Column(name="p_telefono")
	protected String telefono;
	
	@Column(name="p_email")
	protected String email;
	
	@Column(name="p_facebook")
	protected String facebook;
	
	@Column(name="p_instagram")
	protected String instagram;
	
	@Column(name="p_twitter")
	protected String twitter;
	
	@Column(name="p_direccion")
	protected String direccion;
	
	@Column(name="p_localidad")
	protected String localidad;
	
	@Column(name="p_municipio")
	protected String municipio;
	
	@Lob
	@Column(name="p_logo", columnDefinition = "BLOB")
	protected byte[] logo;

	@Column(name="p_mime")
	protected String mime;

	@Column(name="p_nombre_logo")
	protected String nombreLogo;
	
	@Column(name="p_f_baja")
	protected Date fechaBaja;
	
	@Column(name="p_u_creacion")
	@Auditoria("INSERT")
	protected Integer idUsuarioCreacion;
	
	@Column(name="p_f_creacion")
	@Auditoria("INSERT")
	protected Date fechaCreacion;
	
	@Column(name="p_u_actu")
	@Auditoria("UPDATE")
	protected Integer idUsuarioActualizacion;
	
	@Column(name="p_f_actu")
	@Auditoria("UPDATE")
	protected Date fechaActualizacion;
	
	@Transient
	protected String imagen;
	
	@Transient
	protected CommonsMultipartFile archivo;

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

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}	

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getNombreLogo() {
		return nombreLogo;
	}

	public void setNombreLogo(String nombreLogo) {
		this.nombreLogo = nombreLogo;
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

	public CommonsMultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(CommonsMultipartFile archivo) throws IOException {
		this.nombreLogo = archivo.getOriginalFilename();
		this.mime = archivo.getContentType();
		this.logo = new byte[archivo.getInputStream().available()];
		archivo.getInputStream().read(this.logo);
		this.archivo = archivo;
	}

	public String getImagen() {
		return Base64.getEncoder().encodeToString(logo);
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
