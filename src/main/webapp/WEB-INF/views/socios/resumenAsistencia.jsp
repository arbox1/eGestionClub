<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Resumen Asistencias</title>
	
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
			
			$('#tablaAsistencias').on("click", 'td.pago', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				if(data.importe) {
					var fecha = moment().set({'year': data.anyo, 'month': data.mes, 'date': 1}).endOf('month').format('DD/MM/YYYY');
					
					$('#pago').data(data);
					
					$('#pago').cargarDatos({
		    			datos: $.extend({}, data, {
							"fecha": fecha
						})
		    		}).modal("show");
				}
			});
			
			$('#pago').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#pago').enviar("guardar", function(res){
					$('#pago').modal("hide");
					bootbox.alert("Pago realizado correctamente", function(){ 
						$('.buscador form').submit(); 
					});
				});
			}).on('click', '.asistencias', function(e){
				e.stopPropagation();
				var data =  $.extend({}, $('#pago').data(), $(this).data());
				
				$('#asistencias').trigger('reload', data).mostrar();
				
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#asistencias').on("reload", function(e, data){
				e.stopPropagation();
				
				var fechaDesde = moment().set({'year': data.anyo, 'month': data.mes, 'date': 1}).startOf('month');
				var fechaHasta = moment().set({'year': data.anyo, 'month': data.mes, 'date': 1}).endOf('month')
				
				$('#asistencias .modal-title').html("Asistencia de " + data.usuarioNombre + " en " + fechaDesde.format("MMMM") + " de " + data.anyo);
				
		    	$.obtener(data.accion, {
		    		"id": data.usuarioId,
		    		"fechaDesde": fechaDesde,
		    		"fechaHasta": fechaHasta
		    	}, function(res){
		    		$('#asistencias table.detalle').reloadTable(res.resultados.asistencias);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    });
			
			$('#asistencias table.detalle').DataTable({
				language: {
					"emptyTable": "El monitor no tiene asistencia"
				},
    			columns: [
    				{ data: "tarifa.descripcion", title: "Tarifa", className: 'text-center' },
    				{ data: "escuela.descripcion", title: "Escuela", className: 'text-center' },
    	            { data: function(row, type, val, meta){
            			return moment(row.fecha).format('DD/MM/YYYY');
            		}, title: "Fecha", className: 'text-nowrap text-center' },
    	            { data: "horas", title: "Horas", className: 'text-center' },
    	            { data: function(row, type, val, meta){
            			return row.tarifa.importe;
            		}, title: "Importe Tarifa", className: 'importe text-nowrap text-center' },
    	            { data: function(row, type, val, meta){
            			return row.horas * row.tarifa.importe;
            		}, title: "Importe", className: 'importe text-nowrap text-center' },
    	            { data: "observaciones", title: "Observaciones" }
    	        ]
//     	        "footerCallback": function ( row, data, start, end, display ) {
//     	            total = this.api()
// 		                .column(6)//numero de columna a sumar
// 		                //.column(1, {page: 'current'})//para sumar solo la pagina actual
// 		                .data()
// 		                .reduce(function (a, b) {
// 		                    return parseInt(a) + parseInt(b);
// 		                }, 0 );
    	            
    	            
//     	            $(this.api().column(6).footer()).html(total);
//     	        }
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
						<label for="curso" class="col-xs-3 col-sm-3 col-md-2 col-form-label">Curso</label>
						<div class="col-xs-9 col-sm-9 col-md-4">
							<form:select path="observaciones" cssClass="form-control no-limpiar" >
								<form:options items="${cursos}" itemValue="descripcion" itemLabel="descripcion" />
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
						<div class="col-xs-4 col-sm-4 col-md-4 text-left">
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
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-info">
							<div class="panel-body">
								<table class="table table-striped table-bordered socios dataTable no-footer" id="tablaAsistencias">
									<thead>
										<tr>
											<th class="text-center">Nombre</th>
											<c:forEach var="mes" items="${meses}">
												<c:if test="${mes.orden != 0}">
													<th>${mes.descripcion}</th>
												</c:if>
											</c:forEach>
											<th>Total</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="usuario" items="${asistencias}">
											<tr>
												<td>${usuario.monitor.nombreCompleto}</td>
												<c:forEach var="mes" items="${meses}" varStatus="i">
													<c:if test="${mes.orden != 0}">
														<td class="text-right text-nowrap pago" role="button" 
																		data-importe="${usuario.asistencia(mes.numero - 1).importe}"
																		data-mes="${mes.numero - 1}"
																		data-anyo="${usuario.asistencia(mes.numero - 1).anyo}"  
																		data-usuario-id="${usuario.monitor.id}"
																		data-usuario-nombre="${usuario.monitor.nombreCompleto}">
															<fmt:formatNumber pattern="#,##0.00" value="${usuario.asistencia(mes.numero - 1).importe}"/> &euro;
														</td>
													</c:if>
												</c:forEach>
												<td class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${usuario.total}"/> &euro;</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th></th>
											<c:forEach var="mes" items="${meses}">
												<c:if test="${mes.orden != 0}">
													<th class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${mes.total}"/> &euro;</th>
												</c:if>
											</c:forEach>
											<th class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${totales}"/> &euro;</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				No hay datos que mostrar
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="modal" id="pago" tabindex="-1" role="dialog" aria-labelledby="Editar Pago" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Pago</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id"/>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="usuario.id" class="col-sm-4 col-form-label">Usuario:</label>
									<div class="col-sm-8">
										<input type="hidden" name="usuario.id" class="usuarioId">
										<input type="text" class="usuarioNombre form-control required " readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="importe" class="col-sm-4 col-form-label">Importe:</label>
									<div class="col-sm-8">
										<div class="input-group">
											<input type="text" name="importe" class="importe form-control required"/>
											<div class="input-group-append">
												<span class="input-group-text">&euro;</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="fecha" class="col-sm-4 col-form-label">Fecha:</label>
									<div class="col-sm-8">
										<input type="text" name="stringFecha" class="fecha datepicker form-control required"
											data-date-format="mm/dd/yyyy"
											data-date-container="#pago"
											placeholder="dd/mm/aaaa"
										/>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="observacion" class="col-sm-4 col-form-label">Observación:</label>
									<div class="col-sm-8">
										<input type="text" name="observacion" class="observacion form-control"/>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary asistencias" data-accion="asistencias">Asistencias</button>
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#pago form">Limpiar</button>
					<button type="button" class="btn btn-primary btn-fv-submit guardar">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="asistencias" tabindex="-1" role="dialog" aria-labelledby="Asistencias" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Asistencias</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-body">
							<table class="table table-hover table-striped table-bordered dataTable no-footer detalle">
<!-- 								<tfoot> -->
<!-- 									<tr> -->
<!-- 										<th colspan="3" class="text-right">Total:</th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 										<th></th> -->
<!-- 									</tr> -->
<!-- 								</tfoot> -->
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>