package es.arbox.eGestion.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menus_roles")
public class MenuRol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mrol_id")
	protected Integer id;
	
	@ManyToOne
	@JoinColumn(name = "mrol_m_id")
	protected Menu menu;
	
	@ManyToOne
	@JoinColumn(name = "mrol_rol_id")
	protected Rol rol;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
