<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Calendarios</title>
	
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
			
			$("#tablaCalendarios").on("click", ".cargar", function(e){//Cargar
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".eliminar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar el encuentro?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$('.buscador form').submit();
		    			});
		    		}
		    	});
		    }).on("click", ".enviar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea enviar la notificación?", function(result){
		    		if(result){
		    			$.loading("Enviando...")
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$.loaded();
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
		    			datos: res.resultados.calendario
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$("#editar").on("click", ".guardar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
    			$('#editar form').enviar(data.accion, function (res){
    				$('.buscador form').submit();
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
						<label for="curso" class="col-sm-1 col-form-label">Curso</label>
						<div class="col-md-3">
							<form:select path="curso.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${cursos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="escuela" class="col-sm-1 col-form-label">Escuela</label>
						<div class="col-md-3">
							<form:select path="escuela.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${escuelas}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="categoria" class="col-sm-1 col-form-label">Categoría</label>
						<div class="col-md-3">
							<form:select path="categoria.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${categorias}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo Calendario</button>
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
			<c:when test="${calendarios.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida calendarios" id="tablaCalendarios">
							<thead>
								<tr>
									<th class="text-center">Curso</th>
									<th class="text-center">Escuela</th>
									<th class="text-center">Categoria</th>
									<th class="text-center">Fecha</th>
									<th class="text-center">Lugar</th>
									<th class="text-center">Lugar Salida</th>
									<th class="text-center">Hora Salida</th>
									<th class="text-center">Rival</th>
									<th class="text-center">Color</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="calendario" items="${calendarios}">
									<tr>
										<td class="text-center">${calendario.curso.descripcion}</td>
										<td class="text-center">${calendario.escuela.descripcion}</td>
										<td class="text-center">${calendario.categoria.descripcion}</td>
										<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${calendario.fecha}" /></td>
										<td>${calendario.lugar}</td>
										<td>${calendario.lugarSalida}</td>
										<td class="text-center"><fmt:formatDate pattern = "HH:mm" value = "${calendario.fechaSalida}" /></td>
										<td>${calendario.rival}</td>
										<td>${calendario.colorRival}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link enviar" title="Enviar notificación"
												data-accion="notificar" data-id="${calendario.id}">
												<i class="fas fa-envelope"></i>
											</button>
											<button type="button" class="btn btn-link cargar"
												data-accion="cargar" data-id="${calendario.id}">
												<i class="fas fa-pencil-alt"></i>
											</button>
											<button type="button" class="btn btn-link eliminar"
												data-accion="eliminar" data-modelo="nuevo"
												data-id="${calendario.id}">
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Calednario" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Calendario</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id no-limpiar"/>

						<div class="form-group row">
							<label for="curso" class="col-sm-2 col-form-label">Curso</label>
							<div class="col-sm-10">
								<select name="curso.id" class="form-control curso_id required">
									<option value=""></option>
									<c:forEach var="curso" items="${cursos}">
										<option value="${curso.id}">${curso.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="escuela" class="col-sm-2 col-form-label">Escuela</label>
							<div class="col-sm-10">
								<select name="escuela.id" class="form-control escuela_id required">
									<option value=""></option>
									<c:forEach var="escuela" items="${escuelas}">
										<option value="${escuela.id}">${escuela.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="categoria" class="col-sm-2 col-form-label">Categoria</label>
							<div class="col-sm-10">
								<select name="categoria.id" class="form-control categoria_id required">
									<option value=""></option>
									<c:forEach var="categoria" items="${categorias}">
										<option value="${categoria.id}">${categoria.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="lugar" class="col-sm-2 col-form-label">Lugar</label>
							<div class="col-sm-10">
								<input type="text" name="lugar" class="form-control lugar required" />
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fecha" class="col-sm-4 col-form-label">Fecha</label>
									<div class="col-sm-8">
										<input type="text" name="fecha" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#editar'
											class="form-control datepicker fecha_corta fecha fechaValida required" 
											placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="horaInicio" class="col-sm-4 col-form-label">Hora</label>
									<div class="col-sm-8">
										<input type="text" name="horaInicio" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fecha required" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="lugarSalida" class="col-sm-4 col-form-label">Lugar Salida</label>
									<div class="col-sm-8">
										<input type="text" name="lugarSalida" class="form-control lugarSalida required" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="horaSalida" class="col-sm-4 col-form-label">Hora Salida</label>
									<div class="col-sm-8">
										<input type="text" name="horaSalida" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fechaSalida required" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>						
						
						<div class="form-group row">
							<label for="rival" class="col-sm-2 col-form-label">Rival</label>
							<div class="col-sm-10">
								<input type="text" name="rival" class="form-control rival" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="colorRival" class="col-sm-2 col-form-label">Color Rival</label>
							<div class="col-sm-10">
								<input type="text" name="colorRival" class="form-control colorRival" />
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
</body>
</html>