package es.arbox.eGestion.entity.config;

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

import es.arbox.eGestion.entity.BaseEntidad;

@NamedQueries({
	@NamedQuery(
			name="menu.porMenuEstructura",
				query="SELECT m FROM Menu m WHERE m.menuEstructura.id = :idMenuEstructura and exists (select mr from MenuRol mr, UsuarioRol ur where mr.menu.id = m.id and mr.rol.id = ur.rol.id and ur.usuario.id = :idUsuario) order by m.orden "
			),
	@NamedQuery(
			name="menu.porMenuEstructuraSinUsuario",
				query="SELECT m FROM Menu m WHERE m.menuEstructura.id = :idMenuEstructura order by m.orden "
			)
}) 

@Entity
@Table(name = "menus")
public class Menu extends BaseEntidad{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "m_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "m_me_id")
	private MenuEstructura menuEstructura;

	@Column(name = "m_descripcion")
	private String descripcion;
	
	@Column(name = "m_pagina")
	private String pagina;
	
	@Column(name = "m_orden")
	private int orden;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MenuEstructura getMenuEstructura() {
		return menuEstructura;
	}

	public void setMenuEstructura(MenuEstructura menuEstructura) {
		this.menuEstructura = menuEstructura;
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
