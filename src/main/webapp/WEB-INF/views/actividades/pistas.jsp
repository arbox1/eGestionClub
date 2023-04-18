<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Pistas</title>
	
	<script type="text/javascript">
		var dia = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo']; 
		$( document ).ready(function() {
			
			$('#tablaDatos').on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".horarios", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#horarios').trigger("reload", data).mostrar();
		    	
		    }).on("click", ".bloqueos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#bloqueos').trigger("reload", data).mostrar();
		    	
		    }).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				bootbox.confirm("¿Está seguro que desea eliminar?", function(result){
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
		    			datos: res.resultados.dato
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#horarios').on("reload", function(e, data){
				e.stopPropagation();
				$('#horarios').data("id", data.id);
				$('#horario .pista_id').val(data.id);
				$('#copiarHorario .pista_id').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#horarios table.detalle').reloadTable(res.resultados.horarios);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#horarios table.detalle').DataTable({
				language: {
					"emptyTable": "La pista no tiene ningún horario"
				},
    			columns: [
    	            { data: function(row, type, val, meta){
            			return row.dia == null ? '' : dia[row.dia];
            		}, title: "Día", className: 'text-nowrap text-center' },
            		{ data: function(row, type, val, meta){
            			return row.hora == null && row.minuto == null ? '' : String(row.hora).padStart(2, '0') + ":" + String(row.minuto).padStart(2, '0');
            		}, title: "Hora", className: 'text-nowrap text-center' },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'editarHorario',
								title: 'Editar horario',
								data: {
									id: row.id,
									accion: 'editarHorario'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminarHorario',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarHorario'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#horarios').on('click', '.editarHorario', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#horario').cargarDatos({
		    			datos: res.resultados.horario
		    		}).mostrar();
		    	});
			}).on('click', '.eliminarHorario', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el horario?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#horarios').trigger("reload", {id: $('#horarios').data("id"), accion: "cargarHorarios"});
		    			});
		    		}
		    	});
			});
			
			$('#horario').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#horario').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#horario').enviar(data.accion, function(res){
					$('#horarios').trigger("reload", {id: $('#horarios').data("id"), accion: "cargarHorarios"});
					$('#horario').modal("hide");
				});
			});
			
			$('#copiarHorario').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#copiarHorario').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#copiarHorario').enviar(data.accion, function(res){
					$('#horarios').trigger("reload", {id: $('#horarios').data("id"), accion: "cargarHorarios"});
					$('#copiarHorario').modal("hide");
				});
			});
			
			$('#copiarPista').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#copiarPista').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#copiarPista').enviar(data.accion, function(res){
					$('#copiarPista').modal("hide");
				});
			});
			
			$('#bloqueos').on("reload", function(e, data){
				e.stopPropagation();
				$('#bloqueos').data("id", data.id);
				$('#bloqueo .pista_id').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#bloqueos table.detalle').reloadTable(res.resultados.bloqueos);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#bloqueos table.detalle').DataTable({
				language: {
					"emptyTable": "La pista no tiene bloqueos horario"
				},
    			columns: [
    	            { data: function(row, type, val, meta){
            			return moment(row.fechaDesde).format('DD/MM/YYYY HH:mm');
            		}, title: "Fecha Desde", className: 'text-nowrap text-center' },
            		{ data: function(row, type, val, meta){
            			return moment(row.fechaHasta).format('DD/MM/YYYY HH:mm');
            		}, title: "Fecha Hasta", className: 'text-nowrap text-center' },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminarBloqueo',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarBloqueo'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#bloqueos').on('click', '.eliminarBloqueo', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el bloqueo?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#bloqueos').trigger("reload", {id: $('#bloqueos').data("id"), accion: "cargarBloqueos"});
		    			});
		    		}
		    	});
			});
			
			$('#bloqueo').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#bloqueo').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#bloqueo').enviar(data.accion, function(res){
					$('#bloqueos').trigger("reload", {id: $('#bloqueos').data("id"), accion: "cargarBloqueos"});
					$('#bloqueo').modal("hide");
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
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#copiarPista">Copiar Pista</button>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida datos" id="tablaDatos">
							<thead>
								<tr>
									<th class="text-center">Sede</th>
									<th class="text-center">Descripción</th>
									<th class="text-center">Observaciones</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dato" items="${datos}">
									<tr>
										<td class="text-center">${dato.sede.descripcion}</td>
										<td class="">${dato.descripcion}</td>
										<td class="">${dato.observaciones}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${dato.id}" title="Editar Pista"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link horarios" data-accion="cargarHorarios" data-id="${dato.id}" title="Horarios de la pista"><i class="fas fa-calendar"></i></button>
											<button type="button" class="btn btn-link bloqueos" data-accion="cargarBloqueos" data-id="${dato.id}" title="Bloqueos de la pista"><i class="fas fa-lock"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${dato.id}" title="Eliminar Pista"><i class="fas fa-trash"></i></button>
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Tarifa" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="dato">
						<input type="hidden" name="id" class="id">
						
						<div class="form-group row">
							<label for="sede" class="col-sm-2 col-form-label">Sede</label>
							<div class="col-sm-10">
								<select name="sede.id" class="form-control sede_id required">
									<option value=""></option>
									<c:forEach items="${sedes}" var="dato">
										<option value="${dato.id}">${dato.descripcion}</option>		
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripción</label>
							<div class="col-sm-10">
								<input type="text" name="descripcion" class="form-control descripcion required" maxlength="255" />
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Color</label>
							<div class="col-sm-10">
								<input type="text" name="color" class="form-control color required" maxlength="10" />
							</div>
						</div>
						<div class="form-group row">
							<label for="observaciones" class="col-sm-2 col-form-label">Observaciones</label>
							<div class="col-sm-10">
								<textarea rows="3" name="observaciones" class="form-control observaciones required" maxlength="4000"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary" data-submit="#editar form">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="horarios" tabindex="-1" role="dialog" aria-labelledby="Horarios" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Horarios</h5>
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
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#copiarHorario">Copiar Horario</button>
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#horario">Nuevo Horario</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="horario" tabindex="-1" role="dialog" aria-labelledby="Editar Horario" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Horario</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarHorario" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<input type="hidden" name="id" class="id no-limpiar"/>
						<input type="hidden" name="pista.id" class="pista_id no-limpiar"/>

						<div class="row">
							<div class="col-sm">
								<div class="form-group row">
									<label for="dia" class="col-sm-2 col-form-label">Día</label>
									<div class="col-sm-10">
										<select name="dia" class="form-control dia required">
											<option value=""></option>
											<option value="0">Domingo</option>
											<option value="1">Lunes</option>
											<option value="2">Martes</option>
											<option value="3">Miércoles</option>
											<option value="4">Jueves</option>
											<option value="5">Viernes</option>
											<option value="6">Sábado</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="hora" class="col-sm-3 col-form-label">Hora</label>
									<div class="col-sm-9">
										<select name="hora" class="form-control hora required">
											<option value=""></option>
											<option value="1">01</option>
											<option value="2">02</option>
											<option value="3">03</option>
											<option value="4">04</option>
											<option value="5">05</option>
											<option value="6">06</option>
											<option value="7">07</option>
											<option value="8">08</option>
											<option value="9">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
											<option value="24">24</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="minuto" class="col-sm-3 col-form-label">Minuto</label>
									<div class="col-sm-9">
										<select name="minuto" class="form-control minuto required">
											<option value=""></option>
											<option value="0">00</option>
											<option value="15">15</option>
											<option value="30">30</option>
											<option value="45">45</option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarHorario">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="copiarHorario" tabindex="-1" role="dialog" aria-labelledby="Copiar Horario" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Copiar Horario</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="copiarHorario" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<input type="hidden" name="pista.id" class="pista_id no-limpiar"/>

						<div class="row">
							<div class="col-sm">
								<div class="form-group row">
									<label for="dia" class="col-sm-3 col-form-label">Día</label>
									<div class="col-sm-9">
										<select name="dia" class="form-control dia required">
											<option value=""></option>
											<option value="0">Domingo</option>
											<option value="1">Lunes</option>
											<option value="2">Martes</option>
											<option value="3">Miércoles</option>
											<option value="4">Jueves</option>
											<option value="5">Viernes</option>
											<option value="6">Sábado</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="hora" class="col-sm-3 col-form-label">Copiar a</label>
									<div class="col-sm-9">
										<select name="hora" class="form-control hora required">
											<option value=""></option>
											<option value="0">Domingo</option>
											<option value="1">Lunes</option>
											<option value="2">Martes</option>
											<option value="3">Miércoles</option>
											<option value="4">Jueves</option>
											<option value="5">Viernes</option>
											<option value="6">Sábado</option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="copiarHorario">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="copiarPista" tabindex="-1" role="dialog" aria-labelledby="Copiar Pista" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Copiar Pista</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="copiarHorario" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<div class="row">
							<div class="col-sm">
								<div class="form-group row">
									<label for="pista.id" class="col-sm-3 col-form-label">Día</label>
									<div class="col-sm-9">
										<select name="pista.id" class="form-control pista_id required">
											<option value=""></option>
											<c:forEach items="${datos}" var="pista">
												<option value="${pista.id}">${pista.sede.descripcion} - ${pista.descripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="dia" class="col-sm-3 col-form-label">Copiar a</label>
									<div class="col-sm-9">
										<select name="dia" class="form-control dia required">
											<option value=""></option>
											<c:forEach items="${datos}" var="pista">
												<option value="${pista.id}">${pista.sede.descripcion} - ${pista.descripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="copiarPista">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="bloqueos" tabindex="-1" role="dialog" aria-labelledby="Bloqueos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Bloqueos</h5>
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
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#bloqueo">Nuevo Bloqueo</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="bloqueo" tabindex="-1" role="dialog" aria-labelledby="Editar Bloqueo" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Bloqueo</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarBloqueo" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<input type="hidden" name="id" class="id no-limpiar"/>
						<input type="hidden" name="pista.id" class="pista_id no-limpiar"/>
						
						<div class="row">
							<div class="col-sm">
								<div class="form-group row">
									<label for="fechaDesde" class="col-sm-2 col-form-label">Desde</label>
									<div class="col-sm-10">
										<input type="text" name="fechaDesde" 
												data-date-format="mm/dd/yyyy"
												data-date-container='#reserva'
												class="form-control datepicker fecha_corta fechaDesde required" 
												placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="horaDesde" class="col-sm-3 col-form-label">Hora</label>
									<div class="col-sm-9">
										<select name="horaDesde" class="form-control horaDesde required">
											<option value=""></option>
											<option value="1">01</option>
											<option value="2">02</option>
											<option value="3">03</option>
											<option value="4">04</option>
											<option value="5">05</option>
											<option value="6">06</option>
											<option value="7">07</option>
											<option value="8">08</option>
											<option value="9">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
											<option value="24">24</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="minutoDesde" class="col-sm-3 col-form-label">Minuto</label>
									<div class="col-sm-9">
										<select name="minutoDesde" class="form-control minutoDesde required">
											<option value=""></option>
											<option value="0">00</option>
											<option value="15">15</option>
											<option value="30">30</option>
											<option value="45">45</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm">
								<div class="form-group row">
									<label for="fechaHasta" class="col-sm-2 col-form-label">Hasta</label>
									<div class="col-sm-10">
										<input type="text" name="fechaHasta" 
												data-date-format="mm/dd/yyyy"
												data-date-container='#reserva'
												class="form-control datepicker fecha_corta fechaHasta required" 
												placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="horaHasta" class="col-sm-3 col-form-label">Hora</label>
									<div class="col-sm-9">
										<select name="horaHasta" class="form-control horaHasta required">
											<option value=""></option>
											<option value="1">01</option>
											<option value="2">02</option>
											<option value="3">03</option>
											<option value="4">04</option>
											<option value="5">05</option>
											<option value="6">06</option>
											<option value="7">07</option>
											<option value="8">08</option>
											<option value="9">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
											<option value="24">24</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm">
								<div class="form-group row">
									<label for="minutoHasta" class="col-sm-3 col-form-label">Minuto</label>
									<div class="col-sm-9">
										<select name="minutoHasta" class="form-control minutoHasta required">
											<option value=""></option>
											<option value="0">00</option>
											<option value="15">15</option>
											<option value="30">30</option>
											<option value="45">45</option>
										</select>
									</div>
								</div>
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarBloqueo">Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>