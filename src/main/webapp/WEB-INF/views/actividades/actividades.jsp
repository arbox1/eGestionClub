<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Actividades</title>
	
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
			
			$("#tablaActividades").on("click", ".cargar", function(e){//Cargar
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".detalle", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#detalle').trigger("reload", data).mostrar();
		    	
		    }).on("click", ".documentos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#documentos').trigger("reload", data).mostrar();
		    }).on("click", ".eliminar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar la actividad?", function(result){
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
		    }).on("click", ".informe", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea generar el cartel?", function(result){
		    		if(result){
		    			$.enviarForm(data.accion, "valores", data);
		    		}
		    	});
		    });
			
			$('#editar').on("reload", function(e, data){
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.actividad
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    	if($("#editar .fecha").val() == '')
		    		$("#editar .fecha").val(moment().format('DD/MM/YYYY'));
		    });
			
			$("#editar").on("click", ".guardar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
    			$('#editar form').enviar(data.accion, function (res){
    				$('.buscador form').submit();
    			});
		    });
			
			$('#detalle').on("reload", function(e, data){
				e.stopPropagation();
				$('#inscripcion .actividad_id').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#detalle table.detalle').reloadTable(res.resultados.participantes);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    	
		    	if(recargar) {
		    		$.loading("Cargando");
		    		$('.buscador form').submit();
		    	}
		    });
			
			$('#detalle table.detalle').DataTable({
				language: {
					"emptyTable": "la actividad no tiene ningún participante"
				},
    			columns: [
    				{ data: "id", title: "Nº" },
    				{ data: "estado.descripcion", title: "Estado", className: 'text-center' },
    				{ data: "nombre", title: "Nombre" },
    	            { data: "telefono", title: "Teléfono" },
    	            { data: "email", title: "Email" },
    	            { data: "cantidad", title: "Cantidad", className: 'text-nowrap text-center' },
    	            { data: "importe", title: "Importe", className: 'text-nowrap text-center' },
    	            { data: function(row, type, val, meta){
            			return row.lopd != null && row.lopd == 'S' ? 'Si' : 'No';
            		}, title: "Lopd", className: 'text-nowrap text-center' },
    	            { data: function(row, type, val, meta){
    	            			return row.pagado != null && row.pagado == 'S' ? 'Si' : 'No';
    	            		}, title: "Pagado", className: 'text-nowrap text-center' },
    	            { data: function(row, type, val, meta){
    	            			return row.observacion != null && row.observacion.trim() != '' ? 'Si' : 'No';
    	            		}, title: "Obs.", className: 'text-nowrap text-center' },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'inscripcion',
								title: 'Detalle',
								data: {
									id: row.id,
									accion: 'participante'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'key',
								clases: 'password',
								title: 'Enviar nueva contraseña',
								data: {
									id: row.id,
									accion: 'password'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'file-alt',
								clases: 'documentos',
								title: 'Documentos',
								data: {
									id: row.id,
									accion: 'documentosParticipante'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarParticipante'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ],
    	        "footerCallback": function ( row, data, start, end, display ) {
    	            
    	            total = this.api()
    	                .column(5)//numero de columna a sumar
    	                //.column(1, {page: 'current'})//para sumar solo la pagina actual
    	                .data()
    	                .reduce(function (a, b) {
    	                    return parseInt(a) + parseInt(b);
    	                }, 0 );
    	            
    	            $(this.api().column(5).footer()).html(total);
    	            
    	            total = this.api()
		                .column(6)//numero de columna a sumar
		                //.column(1, {page: 'current'})//para sumar solo la pagina actual
		                .data()
		                .reduce(function (a, b) {
		                    return parseInt(a) + parseInt(b);
		                }, 0 );
    	            
    	            
    	            $(this.api().column(6).footer()).html(total);
    	        }
    		});
			
			$('#detalle').on('click', '.inscripcion', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#inscripcion').cargarDatos({
		    			datos: res.resultados.participante
		    		}).mostrar();
		    	});
			}).on('click', '.password', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea cambiar y notificar la nueva contraseña?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#detalle').trigger("reload", {id: $('#inscripcion form .actividad_id').val(), accion: "participantes"});
		    				recargar = true;
		    			});
		    		}
		    	});
			}).on('click', '.documentos', function(e){
				e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#documentosParticipante').trigger("reload", data).mostrar();
			}).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el participante?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#detalle').trigger("reload", {id: $('#inscripcion form .actividad_id').val(), accion: "participantes"});
		    				recargar = true;
		    			});
		    		}
		    	});
			});
			
			$('#inscripcion').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#inscripcion').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#inscripcion').enviar(data.accion, function(res){
					$('#detalle').trigger("reload", {id: res.resultados.id, accion: "participantes"});
					$('#inscripcion').modal("hide");
					recargar = true;
				});
			});
			
			$('#documentos table.detalle').DataTable({
				language: {
					"emptyTable": "No hay documentos de esta actividad"
				},
    			columns: [
    				{ data: "documento.tipo.descripcion", title: "Tipo" },
    	            { data: "documento.descripcion", title: "Descripcion" },
    	            { data: "documento.mime", title: "Mime" },
    	            { data: function ( row, type, val, meta ) {
						var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'search',
								clases: 'descargar',
								title: 'Descargar',
								data: {
									id: row.documento.id,
									accion: 'documento'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									"id": row.id,
									"id-documento": row.documento.id,
									"id-actividad": row.actividad.id,
									accion: 'eliminarDocumento'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
	    	    	}
    	        ]
    		});
			
			$('#documentos').on("reload", function(e, data){
				e.stopPropagation();
				$('#nuevoDocumento .idActividad').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#documentos table.detalle').reloadTable(res.resultados.documento);
		    	});
		    }).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el documento?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id,
		    				"documento.id": $data.idDocumento,
		    				"actividad.id": $data.idActividad
		    			}, function(res){
		    				$('#documentos').trigger("reload", {id: $data.idActividad, accion: "documentos"});
		    			});
		    		}
		    	});
			}).on('click', '.descargar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.enviarForm($data.accion, "valor", {
    				"id": $data.id
    			});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();

		    	$(this, "form").limpiar();
		    });
			
			$('#nuevoDocumento').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#nuevoDocumento').enviar(data.accion, function(res){
					$('#documentos').trigger("reload", {id: res.resultados.id, accion: "documentos"});
					$('#nuevoDocumento').modal("hide");
				});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();

		    	$(this, "form").limpiar();
		    });
			
			$('#documentosParticipante table.detalle').DataTable({
				language: {
					"emptyTable": "No hay documentos de este participante"
				},
    			columns: [
    				{ data: "documento.tipo.descripcion", title: "Tipo" },
    	            { data: "documento.descripcion", title: "Descripcion" },
    	            { data: "documento.mime", title: "Mime" },
    	            { data: function ( row, type, val, meta ) {
						var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'search',
								clases: 'descargar',
								title: 'Descargar',
								data: {
									id: row.documento.id,
									accion: 'documento'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									"id": row.id,
									"id-documento": row.documento.id,
									"id-participante": row.participante.id,
									accion: 'eliminarDocumentoParticipante'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
	    	    	}
    	        ]
    		});
			
			$('#documentosParticipante').on("reload", function(e, data){
				e.stopPropagation();
				$('#nuevoDocumentoParticipante .idParticipante').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#documentosParticipante table.detalle').reloadTable(res.resultados.documento);
		    	});
		    }).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar el documento?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id,
		    				"documento.id": $data.idDocumento,
		    				"actividad.id": $data.idParticipante
		    			}, function(res){
		    				$('#documentosParticipante').trigger("reload", {id: $data.idParticipante, accion: "documentosParticipante"});
		    			});
		    		}
		    	});
			}).on('click', '.descargar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.enviarForm($data.accion, "valor", {
    				"id": $data.id
    			});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();

		    	$(this, "form").limpiar();
		    });
			
			$('#nuevoDocumentoParticipante').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#nuevoDocumentoParticipante').enviar(data.accion, function(res){
					$('#documentosParticipante').trigger("reload", {id: res.resultados.id, accion: "documentosParticipante"});
					$('#nuevoDocumentoParticipante').modal("hide");
				});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();

		    	$(this, "form").limpiar();
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
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-3">
							<form:select path="tipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Actividad</label>
						<div class="col-sm-3">
							<form:input path="descripcion" cssClass="form-control"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nueva Actividad</button>
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
			<c:when test="${actividades.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida actividades" id="tablaActividades">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Estado</th>
									<th class="text-center">Lugar Salida</th>
									<th class="text-center">Precio</th>
									<th class="text-center">Participantes</th>
									<th class="text-center">Inscritos</th>
									<th class="text-center">Fecha Inicio</th>
									<th class="text-center">Fecha Fin</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="actividad" items="${actividades}">
									<tr>
										<td class="text-center">${actividad.tipo.descripcion}</td>
										<td>${actividad.descripcion}</td>
										<td class="text-center">${actividad.estado.descripcion}</td>
										<td>${actividad.lugarSalida}</td>
										<td class="text-center">${actividad.precio} &euro;</td>
										<td class="text-center">${actividad.participantes}</td>
										<td class="text-center">${actividad.inscritos}</td>
										<td class="text-center text-nowrap"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${actividad.fechaInicio}" /></td>
										<td class="text-center text-nowrap"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${actividad.fechaFin}" /></td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link informe" 
												data-accion="informe" 
												data-id="${actividad.id}"
												data-nombre="cartel"
												data-descripcion="reports/actividades/cartel">
												<i class="far fa-file-pdf"></i>
											</button>
											<button type="button" class="btn btn-link enviar" title="Enviar notificación antes de la actividad"
												data-accion="notificarSalida" data-id="${actividad.id}">
												<i class="fas fa-envelope"></i>
											</button>
											<button type="button" class="btn btn-link enviar" title="Enviar notificiación después de la actividad"
												data-accion="notificarLlegada" data-id="${actividad.id}">
												<i class="far fa-envelope"></i>
											</button>
											<button type="button" class="btn btn-link cargar"
												data-accion="cargar" data-id="${actividad.id}">
												<i class="fas fa-pencil-alt"></i>
											</button>
											<button type="button" class="btn btn-link detalle"
												data-accion="participantes" data-id="${actividad.id}">
												<i class="fas fa-bars"></i>
											</button>
											<button type="button" class="btn btn-link documentos" 
												data-accion="documentos" data-id="${actividad.id}">
												<i class="fas fa-file-alt">
											</i></button>
											<button type="button" class="btn btn-link eliminar"
												data-accion="eliminar" data-modelo="nuevo"
												data-id="${actividad.id}">
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Actividad" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Actividad</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<input type="hidden" name="id" class="id no-limpiar"/>

						<div class="form-group row">
							<label for="tipo" class="col-sm-2 col-form-label">Estado</label>
							<div class="col-sm-10">
								<select name="estado.id" class="form-control estado_id required">
									<option value=""></option>
									<c:forEach var="estado" items="${estadosActividad}">
										<option value="${estado.id}">${estado.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="tipo" class="col-sm-2 col-form-label">Tipo</label>
							<div class="col-sm-10">
								<select name="tipo.id" class="form-control tipo_id required">
									<option value=""></option>
									<c:forEach var="tipo" items="${tipos}">
										<option value="${tipo.id}">${tipo.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripci&oacute;n</label>
							<div class="col-sm-10">
								<input type="text" name="descripcion" class="form-control descripcion required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="lugarSalida" class="col-sm-2 col-form-label">Lugar Salida</label>
							<div class="col-sm-10">
								<input type="text" name="lugarSalida" class="form-control lugarSalida" />
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="participantes" class="col-sm-4 col-form-label">Participantes</label>
									<div class="col-sm-8">
										<input type="text" name="participantes" class="form-control participantes required" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="precio" class="col-sm-4 col-form-label">Precio</label>
									<div class="col-sm-8">
										<div class="input-group">
											<input type="text" name="precio" class="form-control precio" />
											<div class="input-group-append">
												<span class="input-group-text">&euro;</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fechaInicio" class="col-sm-4 col-form-label">Fecha Inicio</label>
									<div class="col-sm-8">
										<input type="text" name="fechaInicio" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#editar'
											class="form-control datepicker fecha_corta fechaInicio fechaValida required" 
											placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="horaInicio" class="col-sm-4 col-form-label">Hora Inicio</label>
									<div class="col-sm-8">
										<input type="text" name="horaInicio" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fechaInicio required" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fechaFin" class="col-sm-4 col-form-label">Fecha Fin</label>
									<div class="col-sm-8">
										<input type="text" name="fechaFin" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#editar'
											class="form-control datepicker fecha_corta fechaFin fechaValida required" 
											placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="horaFin" class="col-sm-4 col-form-label">Hora Fin</label>
									<div class="col-sm-8">
										<input type="text" name="horaFin" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fechaFin required" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fechaFin" class="col-sm-4 col-form-label">Fecha Plazo</label>
									<div class="col-sm-8">
										<input type="text" name="fechaFinPlazo" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#editar'
											class="form-control datepicker fecha_corta fechaFinPlazo fechaValida" 
											placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="horaFin" class="col-sm-4 col-form-label">Hora Fin</label>
									<div class="col-sm-8">
										<input type="text" name="horaFinPlazo" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fechaFinPlazo" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="contenido" class="col-sm-2 col-form-label">Observaciones</label>
							<div class="col-sm-10">
								<textarea rows="4" name="contenido" class="form-control contenido"></textarea>
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
	
	<div class="modal" id="detalle" tabindex="-1" role="dialog" aria-labelledby="Participantes" aria-hidden="true" data-keyboard="false" data-backdrop="static">
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
							<table class="table table-striped table-bordered dataTable no-footer detalle">
								<tfoot>
									<tr>
										<th colspan="3" class="text-right">Total:</th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#inscripcion">Nuevo Participante</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="inscripcion" tabindex="-1" role="dialog" aria-labelledby="Participante a la actividad" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Participante</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarParticipante" class="form-horizontal validation" method="post" modelAttribute="participante">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="actividad.id" class="actividad_id no-limpiar"/>
						<div class="form-group row">
							<label for="tipo" class="col-sm-3 col-form-label">Estado</label>
							<div class="col-sm-9">
								<select name="estado.id" class="form-control estado_id required">
									<option value=""></option>
									<c:forEach var="estado" items="${estadosParticipante}">
										<option value="${estado.id}">${estado.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="nombre" class="col-form-label col-md-3">Nombre:</label>
							<div class="col-md-9">
								<input type="text" name="nombre" class="form-control nombre required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="dni" class="col-form-label col-md-3">DNI:</label>
							<div class="col-md-9">
								<input type="text" name="dni" class="form-control dni dniValido" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="fechaNacimiento" class="col-form-label col-md-3">F. Nacimiento:</label>
							<div class="col-md-9">
								<input type="text" name="fechaNacimiento" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#inscripcion'
											class="form-control datepicker fecha_corta fechaNacimiento fechaValida" 
											placeholder="dd/mm/aaaa"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="cantidad" class="col-form-label col-md-3">Cantidad:</label>
							<div class="col-md-9">
								<input type="text" name="cantidad" class="form-control cantidad required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="importe" class="col-form-label col-md-3">Importe:</label>
							<div class="col-md-9">
								<input type="text" name="importe" class="form-control importe noformat required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="pagado" class="col-form-label col-md-3">Pagado:</label>
							<div class="col-md-9">
								<select name="pagado" class="form-control pagado required">
									<option value=""></option>
									<option value="S">Si</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="telefono" class="col-form-label col-md-3">Teléfono:</label>
							<div class="col-md-9">
								<input type="text" name="telefono" class="form-control telefono required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="email" class="col-form-label col-md-3">Email:</label>
							<div class="col-md-9">
								<input type="text" name="email" class="form-control email required" placeholder="ejemplo@host.com" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="lopd" class="col-sm-3 col-form-label">Lopd</label>
							<div class="col-sm-9">
								<select name="lopd" class="form-control lopd required">
									<option value=""></option>
									<option value="S">Si</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="observacion" class="col-form-label col-md-3">Observaciones:</label>
							<div class="col-md-9">
								<textarea rows="4" name="observacion" class="form-control observacion"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarNotificarParticipante" >Guardar y Notificar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarParticipante" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="documentos" tabindex="-1" role="dialog" aria-labelledby="Documentos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Documentos</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row botonera">
					</div>
					<table class="table table-striped table-bordered dataTable no-footer detalle">
					</table>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#nuevoDocumento">Nuevo documento</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="nuevoDocumento" tabindex="-1" role="dialog" aria-labelledby="Nuevo documento" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Nuevo Documento</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarDocumento" cssClass="form-horizontal validation" method="post" modelAttribute="documentoActividad">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="actividad.id" class="idActividad no-limpiar"/>
						
						<div class="form-group row">
							<label for="documento.descripcion" class="col-form-label col-md-3 text-nowrap">Descripcion:</label>
							<div class="col-md-9">
								<input type="text" name="documento.descripcion" class="documento_descripcion required form-control"/>
							</div>
						</div>
						<div class="form-group row">
							<label for="documento.tipo.id" class="col-form-label col-md-3 text-nowrap">Tipo:</label>
							<div class="col-md-9">
								<select name="documento.tipo.id" class="documento_tipo_id required form-control">
									<option value=""></option>
									<c:forEach var="tipo" items="${tiposDocumentos}">
										<option value="${tipo.id}">${tipo.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="documento.fichero" class="col-form-label col-md-3 text-nowrap">Descripcion:</label>
							<div class="col-md-9">
								<input type="file" name="documento.archivo" class="documento_fichero form-control"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#nuevoDocumento">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarDocumento" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="documentosParticipante" tabindex="-1" role="dialog" aria-labelledby="Documentos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Documentos Participante</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row botonera">
					</div>
					<table class="table table-striped table-bordered dataTable no-footer detalle">
					</table>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#nuevoDocumentoParticipante">Nuevo documento</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="nuevoDocumentoParticipante" tabindex="-1" role="dialog" aria-labelledby="Nuevo documento" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Nuevo Documento</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarDocumentoParticipante" cssClass="form-horizontal validation" method="post" modelAttribute="documentoParticipante">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="participante.id" class="idParticipante no-limpiar"/>
						<input type="hidden" name="documento.tipo.id" class="documento_tipo_id no-limpiar" value="7"/>
						
						<div class="form-group row">
							<label for="documento.descripcion" class="col-form-label col-md-3 text-nowrap">Descripcion:</label>
							<div class="col-md-9">
								<input type="text" name="documento.descripcion" class="documento_descripcion required form-control"/>
							</div>
						</div>
						<div class="form-group row">
							<label for="documento.fichero" class="col-form-label col-md-3 text-nowrap">Archivo:</label>
							<div class="col-md-9">
								<input type="file" name="documento.archivo" class="documento_fichero form-control"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#nuevoDocumentoParticipante">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarDocumentoParticipante" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>