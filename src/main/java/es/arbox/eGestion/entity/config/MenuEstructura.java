package es.arbox.eGestion.entity.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="menuEstructura.porTipo",
				query="SELECT m FROM MenuEstructura m WHERE m.tipoMenu.id = :idTipoMenu order by m.orden "
			),
}) 

@Entity
@Table(name = "menu_estructura")
public class MenuEstructura extends BaseEntidad{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "me_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "me_tm_id")
	private TiposMenu tipoMenu;

	@Column(name = "me_descripcion")
	private String descripcion;
	
	@Column(name = "me_orden")
	private int orden;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "menuEstructura")
	@Transient
	private List<Menu> menus = new ArrayList<Menu>();
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TiposMenu getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(TiposMenu tipoMenu) {
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
