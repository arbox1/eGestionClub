<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Socios</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			$("#tablaSocios").on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    });
			
			$("#tablaSocios").on("click", ".detalle", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#detalle').trigger("reload", data).mostrar();
		    });
			
			$("#tablaSocios").on("click", ".documentos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#documentos').trigger("reload", data).mostrar();
		    });
			
			$("#tablaSocios").on("click", ".eliminar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	bootbox.confirm("¿Está seguro que desea eliminar el socio seleccionado?", function(result){
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
		    			datos: res.resultados.socio
		    		});
		    		
		    		if(res.resultados.imagen != null && res.resultados.imagen != ''){
		    			$('#editar .foto').attr("src",'data:'+res.resultados.mime+';base64,'+res.resultados.imagen);
		    			$('#editar .foto').show();
		    		}
		    	});
		    }).on('shown.bs.modal', function(e) {
// 			    e.stopPropagation();
// 			    $('.datepicker').datepicker({
// 		            format: "dd/mm/yyyy",
// 		            todayBtn: "linked",
// 		            container: '#editar .modal-body',
// 		            language: "es",
// 		            autoclose: true,
// 		            todayHighlight: true
// 		        });
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$('#editar .foto').hide();
		    	$(this, "form").limpiar();
		    });
			
			$('#detalle').on("reload", function(e, data){
				e.stopPropagation();
				$('#inscripcion .idSocio').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#detalle table.detalle').reloadTable(res.resultados.sociosCurso);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#inscripcion').on("reload", function(e, data){
				e.stopPropagation();
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#inscripcion').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#inscripcion').enviar(data.accion, function(res){
					$('#detalle').trigger("reload", {id: res.resultados.id, accion: "escuelas"});
					$('#inscripcion').modal("hide");
				});
			});
			
			$('#detalle table.detalle').DataTable({
				language: {
					"emptyTable": "El socio no tiene está en ninguna escuela deportiva"
				},
    			columns: [
    	            { data: "curso.descripcion", title: "Curso" },
    	            { data: "escuela.descripcion", title: "Escuela" },
    	            { data: "categoria.descripcion", title: "Categoria" },
    	            { data: function(row, type, val, meta){
    	            			return row.entrada != null ? row.entrada.descripcion : null;
    	            		}, title: "Entrada" },
    	            { data: function(row, type, val, meta){
    	            			return row.salida != null ? row.salida.descripcion : null;
    	            		}, title: "Salida" },
	   	            { data: function ( row, type, val, meta ) {
							var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'inscripcion',
								title: 'Detalle',
								data: {
									id: row.id,
									accion: 'inscripcion'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'bars',
								clases: 'cuotas',
								title: 'Cuotas',
								data: {
									id: row.id,
									accion: 'cuotas'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarInscripcion'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
					}
    	        ]
    		});
			
			$('#detalle').on('click', '.inscripcion', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#inscripcion').cargarDatos({
		    			datos: res.resultados.socioCurso
		    		}).mostrar();
		    	});
			}).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar la inscripción?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#detalle').trigger("reload", {id: $('#inscripcion form .idSocio').val(), accion: "escuelas"});
		    			});
		    		}
		    	});
			}).on("click", ".cuotas", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#cuotas').trigger("reload", data).mostrar();
		    });
			
			$('#cuotas table.detalle').DataTable({
				language: {
					"emptyTable": "No hay cuotas de este socio para esta escuela deportiva"
				},
    			columns: [
    	            { data: "mes.descripcion", title: "Mes" },
    	            { data: "importe", title: "Importe", render: $.fn.dataTable.render.importe()},
    	            { data: "observaciones", title: "Observaciones" },
    	            { data: function ( row, type, val, meta ) {
						var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'pencil-alt',
								clases: 'editar',
								title: 'Detalle',
								data: {
									id: row.id,
									accion: 'editarCuota'
								}
							}).addBoton({
								tipo: 'link',
								icono: 'trash',
								clases: 'eliminar',
								title: 'Eliminar',
								data: {
									id: row.id,
									accion: 'eliminarCuota'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
	    	    	}
    	        ]
    		});
			
			$('#cuotas').on("reload", function(e, data){
				e.stopPropagation();
				$('#nuevaCuota .idSocioCurso').val(data.id);
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#cuotas table.detalle').reloadTable(res.resultados.cuotas);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();

		    	$(this, "form").limpiar();
		    }).on('click', '.editar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.obtener($data.accion, {
		    		"id": $data.id
		    	}, function(res){
		    		$('#nuevaCuota').cargarDatos({
		    			datos: res.resultados.cuota
		    		}).mostrar();
		    	});
			}).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea eliminar la cuota?", function(result){
		    		if(result){
		    			$.enviarFormAjax($data.accion, {
		    				"id": $data.id
		    			}, function(res){
		    				$('#cuotas').trigger("reload", {id: $('#nuevaCuota form .idSocioCurso').val(), accion: "cuotas"});
		    			});
		    		}
		    	});
			});
			
			$('#nuevaCuota').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#nuevaCuota').enviar(data.accion, function(res){
					$('#cuotas').trigger("reload", {id: res.resultados.id, accion: "cuotas"});
					$('#nuevaCuota').modal("hide");
				});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#documentos table.detalle').DataTable({
				language: {
					"emptyTable": "No hay documentos de este socio"
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
									"id-socio": row.socio.id,
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
				$('#nuevoDocumento .idSocio').val(data.id);
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
		    				"socio.id": $data.idSocio
		    			}, function(res){
		    				$('#documentos').trigger("reload", {id: $data.idSocio, accion: "documentos"});
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
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo Socio</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida socios" id="tablaSocios">
							<thead>
								<tr>
									<th class="text-center">Nombre</th>
									<th class="text-center">Apellidos</th>
									<th class="text-center">Telefono</th>
									<th class="text-center">Email</th>
									<th class="text-center">Sexo</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="socio" items="${socios}">
									<tr>
										<td>${socio.nombre}</td>
										<td>${socio.apellidos}</td>
										<td>${socio.telefono}</td>
										<td>${socio.email}</td>
										<td>${socio.sexo.descripcion}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${socio.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link detalle" data-accion="escuelas" data-id="${socio.id}"><i class="fas fa-bars"></i></button>
											<button type="button" class="btn btn-link documentos" data-accion="documentos" data-id="${socio.id}"><i class="fas fa-file-alt"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${socio.id}"><i class="fas fa-trash"></i></button>
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Socio" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Socio</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="nuevo">
						<form:hidden path="id" cssClass="id" />
						
						<div class="row">
							<div class="col-sm-2">
								<img src='' alt="Mi imagen" class="foto" size="112" height="128" style="display: none;"/>
							</div>
							<div class="col-sm-8">
								<div class="form-group row">
									<label for="nombre" class="col-sm-2 col-form-label">Nombre</label>
									<div class="col-sm-10">
										<form:input path="nombre" cssClass="form-control nombre required" />
									</div>
								</div>
								<div class="form-group row">
									<label for="apellidos" class="col-sm-2 col-form-label">Apellidos</label>
									<div class="col-sm-10">
										<form:input path="apellidos" cssClass="form-control apellidos required" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label for="email" class="col-sm-2 col-form-label">Email:</label>
							<div class="col-sm-10">
								<form:input path="email" cssClass="form-control email required email" placeholder="ejemplo@host.com" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fechaNacimiento" class="col-sm-4 col-form-label">F. Nac.:</label>
									<div class="col-sm-8">
											<form:input path="fechaNacimiento" 
												data-date-format="mm/dd/yyyy"
												data-date-container='#editar'
												cssClass="form-control fecha_corta fechaNacimiento required" 
												placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="telefono" class="col-sm-4 col-form-label">Teléfono:</label>
									<div class="col-sm-8">
										<form:input path="telefono" cssClass="form-control telefono required" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="sexo" class="col-sm-4 col-form-label">Sexo:</label>
									<div class="col-sm-8">
										<form:select path="sexo.id" cssClass="form-control sexo_id required" >
											<form:option value=""></form:option>
											<form:option value="1">Femenino</form:option>
											<form:option value="2">Masculino</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="lopd" class="col-sm-4 col-form-label">Lopd:</label>
									<div class="col-sm-8">
										<form:select path="lopd" cssClass="form-control lopd required" >
											<form:option value=""></form:option>
											<form:option value="1">Si</form:option>
											<form:option value="0">No</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="padre" class="col-sm-4 col-form-label">Padre:</label>
									<div class="col-sm-8">
										<form:input path="padre" cssClass="form-control padre" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="madre" class="col-sm-4 col-form-label">Madre:</label>
									<div class="col-sm-8">
										<form:input path="madre" cssClass="form-control madre" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="telefonoPadre" class="col-sm-4 col-form-label">Tlf. Padre:</label>
									<div class="col-sm-8">
										<form:input path="telefonoPadre" cssClass="form-control telefonoPadre" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="telefonoMadre" class="col-sm-4 col-form-label">Tlf. Madre:</label>
									<div class="col-sm-8">
										<form:input path="telefonoMadre" cssClass="form-control telefonoMadre" />
									</div>
								</div>
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
	
	<div class="modal" id="detalle" tabindex="-1" role="dialog" aria-labelledby="Detalle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Socios Curso</h5>
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
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#inscripcion">Inscribir Socio</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal" id="inscripcion" tabindex="-1" role="dialog" aria-labelledby="Inscripcion de socios" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Inscripcion</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarInscripcion" class="form-horizontal validation" method="post" modelAttribute="inscripcion">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="socio.id" class="idSocio no-limpiar"/>
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3">Curso:</label>
							<div class="col-md-9">
								<select name="curso.id" class="curso_id form-control required">
									<option value=""></option>
									<c:forEach var="curso" items="${cursos}">
										<option value="${curso.id}">${curso.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3">Escuela:</label>
							<div class="col-md-9">
								<select name="escuela.id" class="escuela_id form-control required">
									<option value=""></option>
									<c:forEach var="escuela" items="${escuelas}">
										<option value="${escuela.id}">${escuela.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3">Categoria:</label>
							<div class="col-md-9">
								<select name="categoria.id" class="categoria_id form-control required">
									<option value=""></option>
									<c:forEach var="categoria" items="${categorias}">
										<option value="${categoria.id}">${categoria.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3 text-nowrap">Mes Entrada:</label>
							<div class="col-md-9">
								<select name="entrada.id" class="entrada_id form-control">
									<option value=""></option>
									<c:forEach var="mes" items="${meses}">
										<option value="${mes.id}">${mes.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3 text-nowrap">Mes Salida:</label>
							<div class="col-md-9">
								<select name="salida.id" class="salida_id form-control">
									<option value=""></option>
									<c:forEach var="mes" items="${meses}">
										<option value="${mes.id}">${mes.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#subtipo inscripcion">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarInscripcion" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="cuotas" tabindex="-1" role="dialog" aria-labelledby="Cuotas" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Cuotas</h5>
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
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#nuevaCuota">Nueva cuota</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="nuevaCuota" tabindex="-1" role="dialog" aria-labelledby="Cuota de socio" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Cuota</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarCuota" class="form-horizontal validation" method="post" modelAttribute="cuota">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="socioCurso.id" class="idSocioCurso no-limpiar"/>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3 text-nowrap">Mes:</label>
							<div class="col-md-9">
								<select name="mes.id" class="mes_id form-control required">
									<option value=""></option>
									<c:forEach var="mes" items="${meses}">
										<option value="${mes.id}">${mes.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3 text-nowrap">Importe:</label>
							<div class="col-md-9">
								<div class="input-group">
									<input type="text" name="importe" class="importe form-control required"/>
									<div class="input-group-append">
										<span class="input-group-text">&euro;</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="curso" class="col-form-label col-md-3 text-nowrap">Observaciones:</label>
							<div class="col-md-9">
								<input type="text" name="observaciones" class="observaciones form-control"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#subtipo inscripcion">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarCuota" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="documentos" tabindex="-1" role="dialog" aria-labelledby="Cuotas" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Cuotas</h5>
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
	
	<div class="modal" id="nuevoDocumento" tabindex="-1" role="dialog" aria-labelledby="Cuota de socio" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Cuota</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarDocumento" cssClass="form-horizontal validation" method="post" modelAttribute="documentoSocio">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="socio.id" class="idSocio no-limpiar"/>
						
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
</body>
</html>