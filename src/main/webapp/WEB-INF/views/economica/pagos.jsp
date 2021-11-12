<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Pagos</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
			$('#tablaPagos').on('click', '.eliminar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				bootbox.confirm("¿Está seguro que desea eliminar el gasto seleccionado?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function(res){
							$('.buscador form').submit();
						});
		    		}
		    	});
			});
			
			$('#pago').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
// 				$.loading("Guardando");
				$('#pago').enviar("guardar", function(res){
					$('#editar').modal("hide");
					$('.buscador form').submit();
				});
			}).on('change', '.tarifa_id', function(e){
				e.stopPropagation();
				var string = '#pago .tarifa_'+$(this).val();
				var valor = $(string).data("importe");
				$('#pago .importe').val(valor);
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    	if($("#pago .fecha").val() == '')
		    		$("#pago .fecha").val(moment().format('DD/MM/YYYY'));
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
						<label for="tipo" class="col-sm-1 col-form-label">Usuario</label>
						<div class="col-md-5">
							<form:select path="usuario.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un usuario"/>
								<form:options items="${usuarios}" itemValue="id" itemLabel="nombreCompleto"/>
							</form:select>
						</div>
						<label for="fecha" class="col-sm-1 col-form-label">Mes</label>
						<div class="col-md-5">
							<form:select path="mes" cssClass="form-control" >
								<form:option value="" label="--Selecciona un mes"/>
								<form:options items="${meses}" itemValue="numero" itemLabel="descripcion"/>
							</form:select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6" >
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#pago">Nuevo Pago</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary" data-submit=".buscador form">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<c:choose>
			<c:when test="${pagos.size() > 0}">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-info">
							<div class="panel-body">
								<table class="table table-striped table-bordered extendida pagos" id="tablaPagos">
									<thead>
										<tr>
											<th class="text-center">Usuario</th>
											<th class="text-center">Tarifa</th>
											<th class="text-center">Fecha</th>
											<th class="text-center" >Importe</th>
											<th class="text-center" >Horas</th>
											<th class="text-center sumatorio" data-column="5" >Total</th>
											<th class="text-center" >Observaciones</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="pago" items="${pagos}">
											<tr>
												<td class="text-center">${pago.usuario.nombreCompleto}</td>
												<td class="text-center">${pago.tarifa.descripcion}</td>
												<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${pago.fecha}" /></td>
												<td class="text-right"><fmt:formatNumber pattern="#,##0.00" value="${pago.importe}"/> &euro;</td>
												<td class="text-right">${pago.cantidad}</td>
												<td class="text-right" name="importe"><fmt:formatNumber pattern="#,##0.00" value="${pago.total}"/> &euro;</td>
												<td class="text-right">${pago.observacion}</td>
												<td class="text-center text-nowrap">
		<%-- 											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${ingreso.id}"><i class="fas fa-pencil-alt"></i></button> --%>
													<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${pago.id}"><i class="fas fa-trash"></i></button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th colspan="6" style="text-align:right" rowspan="1">
												Total:
											</th>
											<th rowspan="1" colspan="2"></th></tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				No se han encontrado resultados
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="modal" id="pago" tabindex="-1" role="dialog" aria-labelledby="Editar Gasto" aria-hidden="true">
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
										<select name="usuario.id" class="usuario_id form-control required">
											<option value="">--Selecciona un usuario</option>
											<c:forEach items="${usuarios}" var="usuario">{
												<option value="${usuario.id}">${usuario.nombreCompleto}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="tarifa.id" class="col-sm-4 col-form-label">Tarifa:</label>
									<div class="col-sm-8">
										<select name="tarifa.id" class="tarifa_id form-control required">
											<option value="">--Selecciona una tarifa</option>
											<c:forEach items="${tarifas}" var="tarifa">{
												<option value="${tarifa.id}" class="tarifa_${tarifa.id}" data-importe="${tarifa.importe}">${tarifa.descripcion}</option>
											</c:forEach>
										</select>
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
									<label for="cantidad" class="col-sm-4 col-form-label">Horas:</label>
									<div class="col-sm-8">
										<input type="text" name="cantidad" class="cantidad form-control required"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="fecha" class="col-sm-4 col-form-label">Fecha:</label>
									<div class="col-sm-8">
										<input type="text" name="stringFecha" class="fecha form-control required"
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
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#pago form">Limpiar</button>
					<button type="button" class="btn btn-primary btn-fv-submit guardar">Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>