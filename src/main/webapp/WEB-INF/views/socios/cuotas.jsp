<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Cuotas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			$('.buscador').on('click', '.accion', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('.buscador form').prop('action', data.accion).submit();
				
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#tablaSocios').on("click", 'td.cuota', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('#editar').cargarDatos({
	    			datos: data
	    		}).modal("show");
			});
			
			$('#editar').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#editar').enviar(data.accion, function(res){
					$('#editar').modal("hide");
					$('.buscador form').submit();
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
						<label for="tipo" class="col-sm-1 col-form-label">Curso</label>
						<div class="col-md-3">
							<form:select path="curso.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${cursos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Escuela</label>
						<div class="col-sm-3">
							<form:select path="escuela.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona una escuela"/>
								<form:options items="${escuelas}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Categoría</label>
						<div class="col-sm-3">
							<form:select path="categoria.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona una categoría"/>
								<form:options items="${categorias}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary accion" data-accion="exportarExcel">Excel</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered socios dataTable no-footer" id="tablaSocios">
							<thead>
								<tr>
									<th class="text-center">Nombre</th>
<!-- 									<th></th> -->
									<c:forEach var="mes" items="${meses}">
										<th>${mes.codigo}</th>
									</c:forEach>
									<th>Total</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="socioCurso" items="${sociosCurso}">
									<tr>
										<td>${socioCurso.socio.nombreCompleto}</td>
										<c:forEach var="mes" items="${meses}">
											<td class="text-right text-nowrap cuota" role="button" 
															data-cuota="${socioCurso.cuota(mes.id).id}" 
															data-importe="${socioCurso.cuota(mes.id).importe}"
															data-mes="${mes.id}" 
															data-socio="${socioCurso.id}">
												<fmt:formatNumber pattern="#,##0.00" value="${socioCurso.cuota(mes.id).importe}"/> &euro;
											</td>
										</c:forEach>
										<td class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${socioCurso.total}"/> &euro;</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th></th>
									<c:forEach var="entry" items="${totalesMeses}">
										<th><c:out value="${entry.value}"/></th>
									</c:forEach>
									<th class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${totales}"/> &euro;</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Cuota" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Cuota</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="cuota"/>
						<input type="hidden" name="socioCurso.id" class="socio no-limpiar"/>
						<input type="hidden" name="mes.id" class="mes no-limpiar"/>
						
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
					</form>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-12">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
							<button type="button" class="btn btn-primary btn-fv-submit guardar" data-accion="enviarMailCuota">Guardar y Notificar</button>
							<button type="button" class="btn btn-primary btn-fv-submit guardar" data-accion="guardarCuota">Guardar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>