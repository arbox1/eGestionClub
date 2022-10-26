<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Asistencias Administrador</title>
	
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
			
			$("#tablaAsistencias").on("click", ".cargar", function(e){//Cargar
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".eliminar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar la asistencia?", function(result){
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
		    			datos: res.resultados.asistencia
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    	if($("#editar .fecha").val() == '')
		    		$("#editar .fecha").val(moment().format('DD/MM/YYYY'));
		    });;
			
			$("#editar").on("click", ".guardar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	console.log($('#editar form .horas').val().toString().replace('.', ','));
		    	$('#editar form .horasHidden').val($('#editar form .horas').val().toString().replace('.', ','));
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
						<label for="tarifa" class="col-xs-3 col-sm-3 col-md-2 col-form-label">Usuario</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:select path="monitor.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un usuario"/>
								<form:options items="${usuarios}" itemValue="id" itemLabel="nombreCompleto"/>
							</form:select>
						</div>
					</div>
					<div class="row form-group">
						<label for="tarifa" class="col-xs-3 col-sm-3 col-md-2 col-form-label">Tarifa</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:select path="tarifa.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona una tarifa"/>
								<form:options items="${tarifas}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="escuela" class="col-xs-3 col-sm-3 col-md-2 col-form-label">Escuela</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:select path="escuela.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona una escuela"/>
								<form:options items="${escuelas}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
					</div>
					
					<div class="row form-group">
						<label for="fechaDesde" class="col-xs-3 col-sm-3 col-md-2 col-form-label">F. Desde</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:input path="fechaDesde" cssClass="form-control datepicker" 
								data-date-format="mm/dd/yyyy"
								data-date-container='.buscador'
								placeholder="dd/mm/aaaa" />
						</div>
						<label for="fechaHasta" class="col-xs-3 col-sm-3 col-md-2 col-form-label">F. Hasta</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:input path="fechaHasta" cssClass="form-control datepicker" 
								data-date-format="mm/dd/yyyy"
								data-date-container='.buscador'
								placeholder="dd/mm/aaaa" />
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-xs-4 col-sm-4 col-md-4 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nueva Asistencia</button>
						</div>
						<div class="col-xs-8 col-sm-8 col-md-8 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<c:choose>
			<c:when test="${asistencias.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida asistencias" id="tablaAsistencias">
							<thead>
								<tr>
									<th class="text-center">Usuario</th>
									<th class="text-center">Tarifa</th>
									<th class="text-center">Escuela</th>
									<th class="text-center">Fecha</th>
									<th class="text-center">Hora</th>
									<th class="text-center">Observaciones</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="asistencia" items="${asistencias}">
									<tr>
										<td class="text-center">${asistencia.monitor.nombreCompleto}</td>
										<td class="text-center">${asistencia.tarifa.descripcion}</td>
										<td class="text-center">${asistencia.escuela.descripcion}</td>
										<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${asistencia.fecha}" /></td>
										<td class="text-center">${asistencia.horas}</td>
										<td>${asistencia.observaciones}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar"
												data-accion="cargar" data-id="${asistencia.id}">
												<i class="fas fa-pencil-alt"></i>
											</button>
											<button type="button" class="btn btn-link eliminar"
												data-accion="eliminar" data-modelo="nuevo"
												data-id="${asistencia.id}">
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Asistencia" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Asistencia monitor</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id"/>

						<div class="form-group row">
							<label for="tarifa" class="col-sm-2 col-form-label">Usuario</label>
							<div class="col-sm-10">
								<select name="monitor.id" class="form-control monitor_id required">
									<option value=""></option>
									<c:forEach var="usuario" items="${usuarios}">
										<option value="${usuario.id}">${usuario.nombreCompleto}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label for="tarifa" class="col-sm-2 col-form-label">Tarifa</label>
							<div class="col-sm-10">
								<select name="tarifa.id" class="form-control tarifa_id required">
									<option value=""></option>
									<c:forEach var="tarifa" items="${tarifas}">
										<option value="${tarifa.id}">${tarifa.descripcion}</option>
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
							<label for="fecha" class="col-sm-2 col-form-label">Fecha</label>
							<div class="col-sm-10">
								<input type="text" name="fecha" 
									data-date-format="mm/dd/yyyy"
									data-date-container='#editar'
									class="form-control datepicker fecha_corta fecha fechaValida required" 
									placeholder="dd/mm/aaaa"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="horas" class="col-sm-2 col-form-label">Horas</label>
							<div class="col-sm-10">
								<select class="form-control horas required">
									<option></option>
									<option value="1">1</option>
									<option value="1.5">1.5</option>
									<option value="2">2</option>
									<option value="2.5">2.5</option>
									<option value="3">3</option>
									<option value="3">3.5</option>
									<option value="4">4</option>
									<option value="4.5">4.5</option>
									<option value="5">5</option>
									<option value="5.5">5.5</option>
									<option value="6">6</option>
									<option value="6,5">6.5</option>
									<option value="7">7</option>
									<option value="7.5">7.5</option>
									<option value="8">8</option>
									<option value="8.5">8.5</option>
								</select>
								<input type="hidden" name="horas" class="horasHidden" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="observaciones" class="col-sm-2 col-form-label">Observaciones</label>
							<div class="col-sm-10">
								<input type="text" name="observaciones" class="form-control observaciones" />
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