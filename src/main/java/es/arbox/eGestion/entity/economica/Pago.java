package es.arbox.eGestion.entity.economica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import es.arbox.eGestion.entity.config.Usuario;

@Entity
@Table(name = "pagos")
public class Pago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "p_us_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "p_t_id")
	private Tarifa tarifa;
	
	@Column(name = "p_fecha")
	@JsonDeserialize(using = StringDateConverter.class, as = Date.class)
	private Date fecha;
	
	@Column(name = "p_importe")
	private Double importe;
	
	@Column(name = "p_cantidad")
	private Integer cantidad;
	
	@Column(name = "p_observacion")
	private String observacion;
	
	@Transient
	protected Integer mes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Double getTotal() {
		return (importe != null ? importe : 0) * (cantidad != null ? cantidad : 0);
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	public String getStringFecha() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(fecha);
	}
	
	public void setStringFecha(String f) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		fecha = formatter.parse(f);
	}
}
