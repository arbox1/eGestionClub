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
		    });
			
			$("#tablaIngreso").on("click", ".eliminar", function(e){
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
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida ingresos" id="tablaIngreso">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Importe</th>
									<th class="text-center">Fecha</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ingreso" items="${ingresos}">
									<tr>
										<td class="text-center">${ingreso.subTipo.descripcion}</td>
										<td>${ingreso.descripcion}</td>
										<td class="text-right"><fmt:formatNumber pattern="#,##0.00" value="${ingreso.importe}"/> &euro;</td>
										<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${ingreso.fecha}" /></td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${ingreso.id}"><i class="fas fa-pencil-alt"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${ingreso.id}"><i class="fas fa-trash"></i></button>
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
</body>
</html>