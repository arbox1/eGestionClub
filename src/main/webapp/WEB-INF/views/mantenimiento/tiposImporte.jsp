<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Tipos Importe</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
		    $('#tablaTiposImporte .eliminar').click(function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	bootbox.confirm("¿Está seguro que desea eliminar el tipo de ingreso?", function(result){
		    		if(result){
		    			$.enviarForm(data.accion, data.modelo, {
		    				"id": data.id
		    			});
		    		}
		    	});
		    });
		    
		    $('#tablaTiposImporte .cargar').click(function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#editar').trigger("reload", data).mostrar();
		    });
		    
		    $('#tablaTiposImporte .detalle').click(function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#detalle').trigger("reload", data).mostrar();
		    });
		    
		    $('#editar .btn-guardar').click(function(e){
		    	e.stopPropagation();
		    	
		    	$('#editar form').submit();
		    })
		    
		    $('#editar').on("reload", function(e, data){
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
		    
		    $('#detalle').on("reload", function(e, data){
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#detalle table.detalle').reloadTable(res.resultados.subtipos);
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
		    
		    $('#detalle table.detalle').DataTable({
    			columns: [
    	            { data: "descripcion", title: "Descripción" },
    	            { data: "orden", title: "orden" }
    	        ]
    		});
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-1 col-md-10">
				<div class="row botonera">
					<div class="col-md-12" >
						<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#editar">Añadir Tipo Importe</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida" id="tablaTiposImporte">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Orden</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="tipo" items="${tiposImporte}">
		
									<c:url var="updateLink" value="/customer/updateForm">
										<c:param name="customerId" value="${tipo.id}" />
									</c:url>
		
									<c:url var="deleteLink" value="/mantenimiento/tiposImporte/eliminar">
										<c:param name="tipo" value="${tipo.id}" />
									</c:url>
		
									<tr>
										<td>${tipo.descripcion}</td>
										<td class="text-center">${tipo.orden}</td>
										<td class="text-center">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${tipo.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link detalle" data-accion="subTipos" data-id="${tipo.id}"><i class="fas fa-bars"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-id="${tipo.id}"><i class="fas fa-trash"></i></button>
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

	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Tipo Importe" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Tipo Importe</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="tipo">
						<form:hidden path="id" cssClass="id" />
						<div class="form-group row">
							<label for="descripcion" class="col-form-label col-md-2">Descripción:</label>
							<div class="col-md-10">
								<form:input path="descripcion" cssClass="form-control descripcion required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="orden" class="col-form-label col-md-2">Orden:</label>
							<div class="col-md-2">
								<form:input path="orden" cssClass="form-control orden required number" />
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
	
	<div class="modal" id="detalle" tabindex="-1" role="dialog" aria-labelledby="Subtipos Importe" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Subtipos Importe</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row botonera">
						<div class="col-md-12">
							<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#subtipo">Añadir Subtipo Importe</button>
						</div>
					</div>
					<table class="table table-striped table-bordered detalle">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal" id="subtipo" tabindex="-1" role="dialog" aria-labelledby="Editar Subtipo Importe" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar Subtipo Importe</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarSubTipo" cssClass="form-horizontal validation" method="post" modelAttribute="subtipo">
						<input type="hidden" name="id" class="id"/>
						<div class="form-group row">
							<label for="descripcion" class="col-form-label col-md-2">Descripción:</label>
							<div class="col-md-10">
								<input type="text" name="descripcion" class="form-control descripcion required"/>
							</div>
						</div>
						<div class="form-group row">
							<label for="orden" class="col-form-label col-md-2">Orden:</label>
							<div class="col-md-2">
								<input type="text" name="orden" class="form-control orden required"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#subtipo form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarSubtipo" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>