package es.arbox.eGestion.dto.config;

import java.util.ArrayList;
import java.util.List;

public class MenuEstructuraDTO {
	private int id;
	
	private TiposMenuDTO tipoMenu;

	private String descripcion;
	
	private int orden;
	
	private List<MenuDTO> menus = new ArrayList<MenuDTO>();

	public MenuEstructuraDTO(int id, TiposMenuDTO tipoMenu, String descripcion, int orden) {
		super();
		this.id = id;
		this.tipoMenu = tipoMenu;
		this.descripcion = descripcion;
		this.orden = orden;
	}

	public MenuEstructuraDTO() {
		super();
	}

	public List<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TiposMenuDTO getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(TiposMenuDTO tipoMenu) {
		this.tipoMenu = tipoMenu;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
}
