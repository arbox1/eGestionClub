<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Ligas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
			
			$('.buscador').on('click', '.accion', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('.buscador form').prop('action', data.accion).submit();
				
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$("#tablaLigas").on("click", ".cargar", function(e){//Cargar
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".grupos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#grupos').trigger("reload", data).mostrar();
		    	
		    }).on("click", ".participantes", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#equipos').trigger("reload", data).mostrar();
		    	
		    }).on("click", ".eliminar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar la liga?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$('.buscador form').submit();
		    			});
		    		}
		    	});
		    }).on("click", ".calendario", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea generar el calendario de la liga? Esta acción eliminará todo el calendario si lo existiese de esta liga.", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$('.buscador form').submit();
		    			});
		    		}
		    	});
		    }).on("click", ".eliminarCalendario", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar el calendario de la liga?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$('.buscador form').submit();
		    			});
		    		}
		    	});
		    });
			
			$('#editar').on("reload", function(e, data){
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.liga
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    });;
			
			$("#editar").on("click", ".guardar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
    			$('#editar form').enviar(data.accion, function (res){
    				$('.buscador form').submit();
    			});
		    });
			
			
			//GRUPOS
			$('#grupos').on("reload", function(e, data){
				e.stopPropagation();
				$('#grupo .liga_id').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#grupos table.detalle').reloadTable(res.resultados.grupos);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    	
		    	if(recargar) {
		    		$.loading("Cargando");
		    		$('.buscador form').submit();
		    	}
		    });
			
			$('#grupos table.detalle').DataTable({
				language: {
					"emptyTable": "la liga no tiene ningún grupo."
				},
    			columns: [
    				{ data: "descripcion", title: "Grupo", className: 'text-center' },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'grupo',
								title: 'Detalle',
								data: {
									id: row.id,
									accion: 'grupo'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarGrupo'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#grupos').on('click', '.grupo', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#grupo').cargarDatos({
		    			datos: res.resultados.grupo
		    		}).mostrar();
		    	});
			}).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el grupo?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#grupos').trigger("reload", {id: $('#grupo form .liga_id').val(), accion: "grupos"});
		    				recargar = true;
		    			});
		    		}
		    	});
			});
			
			$('#equipo .grupo_id').select({
				accion: 'selectGrupo',
	    		blank: ' ',
	    		forceSingleSelection: true
	    	});
			
			$('#grupo').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#grupo').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#grupo').enviar(data.accion, function(res){
					$('#grupos').trigger("reload", {id: res.resultados.id, accion: "grupos"});
					$('#grupo').modal("hide");
					recargar = true;
				});
			});
			
			//EQUIPOS
			$('#equipos').on("reload", function(e, data){
				e.stopPropagation();
				$(this).data('id', data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#equipos table.detalle').reloadTable(res.resultados.equipos);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    	
		    	if(recargar) {
		    		$.loading("Cargando");
		    		$('.buscador form').submit();
		    	}
		    });
			
			$('#equipos table.detalle').DataTable({
				language: {
					"emptyTable": "la liga no tiene ningún participante."
				},
    			columns: [
    				{ data: "grupo.descripcion", title: "Grupo", className: 'text-center' },
    				{ data: "descripcion", title: "Participante", className: '' },
    				{ data: "telefono", title: "Teléfono", className: 'text-center' },
    				{ data: "representante", title: "Representante", className: 'text-center' },
    				{ data: "email", title: "Email", className: 'text-center' },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'equipo',
								title: 'Detalle',
								data: {
									id: row.id,
									accion: 'equipo'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarEquipo'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#equipos').on('click', '.equipo', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#equipo').cargarDatos({
		    			datos: res.resultados.equipo
		    		}).mostrar();
		    	});
			}).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el participante?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#equipos').trigger("reload", {id: $('#equipos').data('id'), accion: "equipos"});
		    				recargar = true;
		    			});
		    		}
		    	});
			});
			
			$('#equipo').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$('#equipo .grupo_id').select("cargar", {
		    		id: $('#equipos').data("id")
		    	});
		    });
			
			$('#equipo').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#equipo').enviar(data.accion, function(res){
					$('#equipos').trigger("reload", {id: $('#equipos').data('id'), accion: "equipos"});
					$('#equipo').modal("hide");
					recargar = true;
				});
			});
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row buscador">
			<div class="col-md-12">
				<form:form action="buscar" cssClass="form-horizontal" method="post" modelAttribute="buscador">
					<div class="row form-group">
						<label for="tipo" class="col-sm-1 col-form-label">Estado</label>
						<div class="col-md-3">
							<form:select path="estado.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona estado"/>
								<form:options items="${estados}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-3">
							<form:select path="tipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Descripción</label>
						<div class="col-sm-3">
							<form:input path="descripcion" cssClass="form-control"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nueva Liga</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<c:choose>
			<c:when test="${ligas.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered table-hover extendida ligas" id="tablaLigas">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Estado</th>
									<th class="text-center">Lugar</th>
									<th class="text-center">Grupos</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="liga" items="${ligas}">
									<tr>
										<td class="text-center">${liga.tipo.descripcion}</td>
										<td>${liga.descripcion}</td>
										<td class="text-center">${liga.estado.descripcion}</td>
										<td>${liga.lugar}</td>
										<td class="text-center">0</td>
										<td class="text-center text-nowrap">
											<c:if test="${liga.estado.id == 1}">
												<button type="button" class="btn btn-link eliminarCalendario" title="Eliminar calendario"
													data-accion="eliminarCalendario" data-id="${liga.id}">
													<i class="fas fa-eraser"></i>
												</button>
												<button type="button" class="btn btn-link calendario" title="Generar calendario"
													data-accion="calendario" data-id="${liga.id}">
													<i class="fas fa-retweet"></i>
												</button>
											</c:if>
											<button type="button" class="btn btn-link grupos" title="Ver grupos"
												data-accion="grupos" data-id="${liga.id}">
												<i class="fas fa-layer-group"></i>
											</button>
											<button type="button" class="btn btn-link participantes" title="Participantes"
												data-accion="equipos" data-id="${liga.id}">
												<i class="fas fa-users"></i>
											</button>
											<button type="button" class="btn btn-link cargar" title="Editar liga"
												data-accion="cargar" data-id="${liga.id}">
												<i class="fas fa-pencil-alt"></i>
											</button>
											<button type="button" class="btn btn-link eliminar" title="Eliminar liga"
												data-accion="eliminar" data-modelo="nuevo"
												data-id="${liga.id}">
												<i class="fas fa-trash"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				No hay datos que mostrar
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Liga" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Liga</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id"/>

						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="estado" class="col-sm-4 col-form-label">Estado</label>
									<div class="col-sm-8">
										<select name="estado.id" class="form-control estado_id required">
											<option value=""></option>
											<c:forEach var="i" items="${estados}">
												<option value="${i.id}">${i.descripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="idaVuelta" class="col-sm-4 col-form-label">Modo</label>
									<div class="col-sm-8">
										<select name="idaVuelta" class="form-control idaVuelta required">
											<option value=""></option>
											<option value="N">Solo ida</option>
											<option value="S">Ida y vuelta</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label for="tipo" class="col-sm-2 col-form-label">Tipo</label>
							<div class="col-sm-10">
								<select name="tipo.id" class="form-control tipo_id required">
									<option value=""></option>
									<c:forEach var="i" items="${tipos}">
										<option value="${i.id}">${i.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripción</label>
							<div class="col-sm-10">
								<input type="text" name="descripcion" class="form-control descripcion" />
							</div>
						</div>
						<div class="form-group row">
							<label for="lugar" class="col-sm-2 col-form-label">Lugar</label>
							<div class="col-sm-10">
								<input type="text" name="lugar" class="form-control lugar" />
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="grupos" class="col-sm-4 col-form-label">Grupos</label>
									<div class="col-sm-8">
										<input type="text" name="grupos" class="form-control grupos" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="set" class="col-sm-4 col-form-label">Set</label>
									<div class="col-sm-8">
										<input type="text" name="set" class="form-control set" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="puntosPartidoGanado" class="col-sm-6 col-form-label">Puntos por ganar</label>
									<div class="col-sm-6">
										<input type="text" name="puntosPartidoGanado" class="form-control puntosPartidoGanado" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="puntosPartidoPerdido" class="col-sm-6 col-form-label">Puntos por perder</label>
									<div class="col-sm-6">
										<input type="text" name="puntosPartidoPerdido" class="form-control puntosPartidoPerdido" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="puntosPartidoEmpatado" class="col-sm-6 col-form-label">Puntos por empatar</label>
									<div class="col-sm-6">
										<input type="text" name="puntosPartidoEmpatado" class="form-control puntosPartidoEmpatado" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="puntosPartidoNoPresentado" class="col-sm-6 col-form-label text-nowrap">Puntos por no presentado</label>
									<div class="col-sm-6">
										<input type="text" name="puntosPartidoNoPresentado" class="form-control puntosPartidoNoPresentado" />
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardar">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="grupos" tabindex="-1" role="dialog" aria-labelledby="Grupos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Grupos</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-body">
							<table class="table table-hover table-striped table-bordered dataTable no-footer detalle">
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#grupo">Nuevo Grupo</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="grupo" tabindex="-1" role="dialog" aria-labelledby="Edición de grupo" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Edición de grupo</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarGrupo" class="form-horizontal validation" method="post" modelAttribute="grupo">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="liga.id" class="liga_id no-limpiar"/>
						<div class="form-group row">
							<label for="descripcion" class="col-form-label col-md-3">Descripción:</label>
							<div class="col-md-9">
								<input type="text" name="descripcion" class="form-control descripcion required"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarGrupo" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="equipos" tabindex="-1" role="dialog" aria-labelledby="Participantes" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Participantes</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-body">
							<table class="table table-hover table-striped table-bordered dataTable no-footer detalle">
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#equipo">Nuevo Participante</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="equipo" tabindex="-1" role="dialog" aria-labelledby="Edición de equipo" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Edición de equipo</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarEquipo" class="form-horizontal validation" method="post" modelAttribute="equipo">
						<input type="hidden" name="id" class="id"/>
						<div class="form-group row">
							<label for="grupo.id" class="col-form-label col-md-3">Grupo:</label>
							<div class="col-md-9">
								<select name="grupo.id" class="form-control grupo_id required"></select>
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-form-label col-md-3">Descripción:</label>
							<div class="col-md-9">
								<input type="text" name="descripcion" class="form-control descripcion required"></textarea>
							</div>
						</div>
						<div class="form-group row">
							<label for="telefono" class="col-form-label col-md-3">Teléfono:</label>
							<div class="col-md-9">
								<input type="text" name="telefono" class="form-control telefono required"></textarea>
							</div>
						</div>
						<div class="form-group row">
							<label for="representante" class="col-form-label col-md-3">Representante:</label>
							<div class="col-md-9">
								<input type="text" name="representante" class="form-control representante required"></textarea>
							</div>
						</div>
						<div class="form-group row">
							<label for="email" class="col-form-label col-md-3">Email:</label>
							<div class="col-md-9">
								<input type="text" name="email" class="form-control email required"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarEquipo" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>