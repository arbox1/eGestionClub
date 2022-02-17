<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Tipos de Documentos</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
			$('#tablaTiposDocumentos').on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				bootbox.confirm("¿Está seguro que desea eliminar el tipo de documento?", function(result){
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
		    			datos: res.resultados.tipo
		    		});
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
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo Tipo de Documento</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida tipos" id="tablaTiposDocumentos">
							<thead>
								<tr>
									<th class="text-center">Código</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Familia</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="tipo" items="${tipos}">
									<tr>
										<td class="text-center">${tipo.codigo}</td>
										<td >${tipo.descripcion}</td>
										<td class="text-center">${tipo.familia.descripcion}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${tipo.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${tipo.id}"><i class="fas fa-trash"></i></button>
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Tipo de Documento" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Tipo de Documento</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="tipo">
						<form:hidden path="id" cssClass="id" />
						
						<div class="form-group row">
							<label for="familia.id" class="col-sm-2 col-form-label">Familia</label>
							<div class="col-sm-10">
								<form:select path="familia.id" cssClass="form-control familia_id required">
									<form:option value="" label="--Selecciona una familia"/>
									<form:options items="${familias}" itemValue="id" itemLabel="descripcion"/>
								</form:select>
							</div>
						</div>
						<div class="form-group row">
							<label for="codigo" class="col-sm-2 col-form-label">Código</label>
							<div class="col-sm-10">
								<form:input path="codigo" cssClass="form-control codigo required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripción</label>
							<div class="col-sm-10">
								<form:input path="descripcion" cssClass="form-control descripcion required" />
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
</body>
</html>