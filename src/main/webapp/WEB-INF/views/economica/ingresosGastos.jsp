<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Ingresos / Gastos</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			$("#tablaIngreso").on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".documentos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#documentos').trigger("reload", data).mostrar();
		    }).on("click", ".eliminar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	bootbox.confirm("&#191Est&aacute; seguro que desea eliminar el gasto seleccionado?", function(result){
		    		if(result){
		    			$.enviarForm(data.accion, data.modelo, {
		    				"id": data.id
		    			});
		    		}
		    	});
		    });
			
			$('#editar').on("reload", function(e, data){
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.ingresoGasto
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
			
			$('#documentos table.detalle').DataTable({
				language: {
					"emptyTable": "No hay documentos de este ingreso / gasto"
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
									"id-ingreso-gasto": row.ingresoGasto.id,
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
				$('#nuevoDocumento .idIngresoGasto').val(data.id);
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
		    				"ingresoGasto.id": $data.idIngresoGasto
		    			}, function(res){
		    				$('#documentos').trigger("reload", {id: $data.idIngresoGasto, accion: "documentos"});
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
		<div class="row buscador">
			<div class="col-md-12">
				<form:form action="buscar" cssClass="form-horizontal" method="post" modelAttribute="buscador">
					<div class="row form-group">
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-5">
							<form:select path="subTipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un tipo"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcionLarga"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Descripci&oacute;n</label>
						<div class="col-sm-5">
							<form:input path="descripcion" cssClass="form-control" />
						</div>
					</div>
					<div class="row form-group">
						<label for="fecha" class="col-sm-1 col-form-label">Desde</label>
						<div class="col-md-5">
							<form:input path="fechaDesde" 
									data-date-format="mm/dd/yyyy"
									data-date-container='#container'
									cssClass="form-control fecha_corta fecha fechaValida datepicker required" 
									placeholder="dd/mm/aaaa"/>
						</div>
						<label for="fecha" class="col-sm-1 col-form-label">Hasta</label>
						<div class="col-md-5">
							<form:input path="fechaHasta" 
									data-date-format="mm/dd/yyyy"
									data-date-container='#container'
									cssClass="form-control fecha_corta fecha fechaValida datepicker required" 
									placeholder="dd/mm/aaaa"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6" >
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo Ingreso / Gasto</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary" data-submit=".buscador form">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida ingresos" id="tablaIngreso">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center sumatorio" data-column="2" >Importe</th>
									<th class="text-center">Fecha</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ingreso" items="${ingresos}">
									<tr>
										<td class="text-center">${ingreso.subTipo.descripcion}</td>
										<td>${ingreso.descripcion}</td>
										<td class="text-right" name="importe"><fmt:formatNumber pattern="#,##0.00" value="${ingreso.importe}"/> &euro;</td>
										<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${ingreso.fecha}" /></td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${ingreso.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link documentos" 
												data-accion="documentos" data-id="${ingreso.id}">
												<i class="fas fa-file-alt">
											</i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${ingreso.id}"><i class="fas fa-trash"></i></button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="4" style="text-align:right" rowspan="1">
										Total:
									</th>
									<th rowspan="1" colspan="1"></th></tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Gasto" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Ingreso / Gasto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="nuevo">
						<form:hidden path="id" cssClass="id" />

						<div class="form-group row">
							<label for="tipo" class="col-sm-2 col-form-label">Tipo</label>
							<div class="col-sm-10">
								<form:select path="subTipo.id" cssClass="form-control subTipo_id required" >
									<form:option value="" label="--Selecciona un tipo"/>
									<form:options items="${tipos}" itemValue="id" itemLabel="descripcionLarga"/>
								</form:select>
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripci&oacute;n</label>
							<div class="col-sm-10">
								<form:input path="descripcion" cssClass="form-control descripcion required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="importe" class="col-sm-2 col-form-label">Importe</label>
							<div class="col-sm-10">
								<form:input path="importe" cssClass="form-control importe required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="observaciones" class="col-sm-2 col-form-label">Observaciones</label>
							<div class="col-sm-10">
								<form:input path="observaciones" cssClass="form-control observaciones" />
							</div>
						</div>
						<div class="form-group row">
							<label for="fecha" class="col-sm-2 col-form-label">Fecha</label>
							<div class="col-sm-10">
								<form:input path="fecha" 
									data-date-format="mm/dd/yyyy"
									data-date-container='#editar'
									cssClass="form-control fecha_corta fecha fechaValida required" 
									placeholder="dd/mm/aaaa"/>
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
					<form action="guardarDocumento" cssClass="form-horizontal validation" method="post" modelAttribute="documentoIngresoGasto">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="ingresoGasto.id" class="idIngresoGasto no-limpiar"/>
						
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