package es.arbox.eGestion.dto.config;

public class MenuDTO {
	
	private int id;

	private String descripcion;
	
	private String pagina;
	
	private int orden;

	public MenuDTO(int id, String descripcion, String pagina, int orden) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.pagina = pagina;
		this.orden = orden;
	}

	public MenuDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
}
