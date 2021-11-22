<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Menus</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			$("#tablaMenus").on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    });
			
			$("#tablaMenus").on("click", ".detalle", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#detalle').trigger("reload", data).mostrar();
		    	
		    }).on("click", ".eliminar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	bootbox.confirm("¿Está seguro que desea eliminar el menu seleccionado?", function(result){
		    		if(result){
		    			$.enviarForm(data.accion, data.modelo, {
		    				"id": data.id
		    			});
		    		}
		    	});
		    });
			
			$('#editar').on("reload", function(e, data){
				e.stopPropagation();
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.menu
		    		});
		    	});
		    }).on('shown.bs.modal', function(e) {
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#detalle').on("reload", function(e, data){
				e.stopPropagation();
				$('#nuevoRol .menu_id').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#detalle table.detalle').reloadTable(res.resultados.roles);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#detalle table.detalle').DataTable({
				language: {
					"emptyTable": "El menu no tiene ningún rol"
				},
    			columns: [
    	            { data: "rol.descripcion", title: "Rol" },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarRol'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#detalle').on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el rol?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#detalle').trigger("reload", {id: $('#nuevoRol form .menu_id').val(), accion: "roles"});
		    			});
		    		}
		    	});
			});
			
			$('#nuevoRol').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#nuevoRol').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#nuevoRol').enviar(data.accion, function(res){
					$('#detalle').trigger("reload", {id: res.resultados.id, accion: "roles"});
					$('#nuevoRol').modal("hide");
				});
			});
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="row botonera">
					<div class="col-md-12" >
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo Menu</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida menus" id="tablaMenus">
							<thead>
								<tr>
									<th class="text-center">Menu Padre</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Pagina</th>
									<th class="text-center">Orden</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="menu" items="${menus}">
									<tr>
										<td>${menu.menuEstructura.descripcion}</td>
										<td>${menu.descripcion}</td>
										<td>${menu.pagina}</td>
										<td>${menu.orden}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${menu.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link detalle"
												data-accion="roles" data-id="${menu.id}">
												<i class="fas fa-bars"></i>
											</button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${menu.id}"><i class="fas fa-trash"></i></button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Menu" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Menu</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="menu">
						<form:hidden path="id" cssClass="id" />
						
						<div class="form-group row">
							<label for="menuEstructura.id" class="col-sm-2 col-form-label">Menu Padre</label>
							<div class="col-sm-10">
								<form:select path="menuEstructura.id" cssClass="form-control menuEstructura_id required">
									<form:option value="" label="--Selecciona un menu"/>
									<form:options items="${menusEstructura}" itemValue="id" itemLabel="descripcion"/>
								</form:select>
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Menu</label>
							<div class="col-sm-10">
								<form:input path="descripcion" cssClass="form-control descripcion required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="pagina" class="col-sm-2 col-form-label">Pagina</label>
							<div class="col-sm-10">
								<form:input path="pagina" cssClass="form-control pagina required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="orden" class="col-sm-2 col-form-label">Orden</label>
							<div class="col-sm-10">
								<form:input path="orden" cssClass="form-control orden required" />
							</div>
						</div>
					</form:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary" data-submit="#editar form">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="detalle" tabindex="-1" role="dialog" aria-labelledby="Roles" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Roles</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-body">
							<table class="table table-striped table-bordered dataTable no-footer detalle">
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#nuevoRol">Nuevo Rol</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="nuevoRol" tabindex="-1" role="dialog" aria-labelledby="Nuevo rol" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Rol</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarRol" class="form-horizontal validation" method="post" modelAttribute="menuRol">
						<input type="hidden" name="id" class="id no-limpiar"/>
						<input type="hidden" name="menu.id" class="menu_id no-limpiar"/>
						<div class="form-group row">
							<label for="rol" class="col-form-label col-md-3">Rol:</label>
							<div class="col-md-9">
								<select name="rol.id" class="rol_id required form-control">
									<option value=""></option>
									<c:forEach var="rol" items="${roles}">
										<option value="${rol.id}">${rol.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#nuevoRol form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarRol" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>