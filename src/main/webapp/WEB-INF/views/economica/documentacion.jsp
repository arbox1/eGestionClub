<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Documentos</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
			$('#tablaDocumentos').on('click', '.eliminar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				bootbox.confirm("¿Está seguro que desea eliminar el documento?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function(res){
							$('.buscador form').submit();
						});
		    		}
		    	});
			}).on('click', '.descargar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.enviarForm($data.accion, "valor", {
    				"id": $data.id
    			});
			});
			
			$('#documento').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
// 				$.loading("Guardando");
				$('#documento').enviar("guardar", function(res){
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
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-5">
							<form:select path="tipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un tipo"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="fecha" class="col-sm-1 col-form-label">Descripción</label>
						<div class="col-md-5">
							<form:input path="descripcion" cssClass="form-control" />
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6" >
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#documento">Nuevo Documento</button>
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
			<c:when test="${documentos.size() > 0}">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-info">
							<div class="panel-body">
								<table class="table table-striped table-bordered extendida documentos" id="tablaDocumentos">
									<thead>
										<tr>
											<th class="text-center">Tipo</th>
											<th class="text-center">Nombre</th>
											<th class="text-center">Descripción</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="documento" items="${documentos}">
											<tr>
												<td class="text-center">${documento.tipo.descripcion}</td>
												<td class="text-center">${documento.nombre}</td>
												<td class="text-center">${documento.descripcion}</td>
												<td class="text-center text-nowrap">
													<button type="button" class="btn btn-link descargar" data-accion="documento" data-modelo="nuevo" data-id="${documento.id}"><i class="fas fa-search"></i></button>
													<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${documento.id}"><i class="fas fa-trash"></i></button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
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
	
	<div class="modal" id="documento" tabindex="-1" role="dialog" aria-labelledby="Editar Documento" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Documento</h5>
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
									<label for="tipo.id" class="col-sm-4 col-form-label">Tipo:</label>
									<div class="col-sm-8">
										<select name="tipo.id" class="tipo_id form-control required">
											<option value="">--Selecciona un tipo</option>
											<c:forEach items="${tipos}" var="tipo">{
												<option value="${tipo.id}">${tipo.descripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="descripcion" class="col-sm-4 col-form-label">Descripción:</label>
									<div class="col-sm-8">
										<input type="text" name="descripcion" class="descripcion form-control required"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="documento.fichero" class="col-form-label col-md-4 text-nowrap">Documento:</label>
									<div class="col-md-8">
										<input type="file" name="archivo" class="archivo form-control"/>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#documento form">Limpiar</button>
					<button type="button" class="btn btn-primary btn-fv-submit guardar">Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>