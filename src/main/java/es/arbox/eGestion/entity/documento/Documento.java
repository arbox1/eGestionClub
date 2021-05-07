package es.arbox.eGestion.entity.documento;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "documentos")
public class Documento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="doc_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "doc_td_id")
	protected TipoDocumento tipo;
	
	@Column(name="doc_nombre")
	protected String nombre;
	
	@Column(name="doc_mime")
	protected String mime;
	
	@Column(name="doc_descripcion")
	protected String descripcion;
	
	@Lob
	@Column(name="doc_fichero", columnDefinition = "BLOB")
	protected byte[] fichero;
	
	@Transient
	CommonsMultipartFile archivo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public byte[] getFichero() {
		return fichero;
	}

	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CommonsMultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(CommonsMultipartFile archivo) throws IOException {
		this.nombre = archivo.getOriginalFilename();
		this.mime = archivo.getContentType();
		this.fichero = new byte[archivo.getInputStream().available()];
		archivo.getInputStream().read(this.fichero);
		this.archivo = archivo;
	}
}
