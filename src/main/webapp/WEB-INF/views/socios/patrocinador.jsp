<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Patrocinadores</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
			$('#tablaDatos').on("click", ".cargar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on('click', '.eliminar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				bootbox.confirm("¿Está seguro que desea eliminar?", function(result){
		    		if(result){
		    			$.enviarForm(data.accion, data.modelo, {
		    				"id": data.id
		    			});
		    		}
		    	});
			}).on("click", ".documentos", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#nuevoDocumento .id').val(data.id);
		    	
		    	$('#nuevoDocumento').mostrar();
		    }).on("click", ".verLogo", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	var $data = $(this).data();
				$.enviarForm($data.accion, "valor", {
    				"id": $data.id
    			});
		    });
			
			$('#editar').on("reload", function(e, data){
				e.stopPropagation();
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.dato
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    });
			
			$('#nuevoDocumento').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#nuevoDocumento').enviar(data.accion, function(res){
					location.href=location.href;
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
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nuevo</button>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida datos" id="tablaDatos">
							<thead>
								<tr>
									<th class="text-center">Descripción</th>
									<th class="text-center">Telefono</th>
									<th class="text-center">Email</th>
									<th class="text-center">Dirección</th>
									<th class="text-center">Localidad</th>
									<th class="text-center">Municipio</th>
									<th class="text-center">F. Baja</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dato" items="${datos}">
									<tr>
										<td class="text-center">${dato.descripcion}</td>
										<td class="text-center">${dato.telefono}</td>
										<td class="text-center">${dato.email}</td>
										<td class="text-center">${dato.direccion}</td>
										<td class="text-center">${dato.localidad}</td>
										<td class="text-center">${dato.municipio}</td>
										<td class="text-center"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${dato.fechaBaja}" /></td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar" data-accion="cargar" data-id="${dato.id}" title="Editar Patrocinador"><i class="fas fa-pencil-alt"></i></button>
											<c:if test="${dato.logo != null}">
												<button type="button" class="btn btn-link verLogo" data-accion="logo" data-id="${dato.id}" title="Ver logo"><i class="fas fa-search"></i></button>
											</c:if>
											<button type="button" class="btn btn-link documentos" data-id="${dato.id}" title="Subir logo"><i class="fas fa-file-alt"></i></button>
											<button type="button" class="btn btn-link eliminar" data-accion="eliminar" data-modelo="nuevo" data-id="${dato.id}" title="Eliminar Patrocinador"><i class="fas fa-trash"></i></button>
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
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Patrocinador" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Editar</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form:form action="guardar" cssClass="form-horizontal validation" method="post" modelAttribute="dato">
						<form:hidden path="id" cssClass="id" />
						
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripción</label>
							<div class="col-sm-10">
								<form:input path="descripcion" cssClass="form-control descripcion required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="telefono" class="col-sm-2 col-form-label">Teléfono</label>
							<div class="col-sm-10">
								<form:input path="telefono" cssClass="form-control telefono" />
							</div>
						</div>
						<div class="form-group row">
							<label for="email" class="col-sm-2 col-form-label">Email</label>
							<div class="col-sm-10">
								<form:input path="email" cssClass="form-control email" placeholder="a@a.com" />
							</div>
						</div>
						<div class="form-group row">
							<label for="facebook" class="col-sm-2 col-form-label">Facebook</label>
							<div class="col-sm-10">
								<form:input path="facebook" cssClass="form-control facebook" />
							</div>
						</div>
						<div class="form-group row">
							<label for="instagram" class="col-sm-2 col-form-label">Instagram</label>
							<div class="col-sm-10">
								<form:input path="instagram" cssClass="form-control instagram" />
							</div>
						</div>
						<div class="form-group row">
							<label for="twitter" class="col-sm-2 col-form-label">Twitter</label>
							<div class="col-sm-10">
								<form:input path="twitter" cssClass="form-control twitter" />
							</div>
						</div>
						<div class="form-group row">
							<label for="direccion" class="col-sm-2 col-form-label">Dirección</label>
							<div class="col-sm-10">
								<form:input path="direccion" cssClass="form-control direccion" />
							</div>
						</div>
						<div class="form-group row">
							<label for="localidad" class="col-sm-2 col-form-label">Localidad</label>
							<div class="col-sm-10">
								<form:input path="localidad" cssClass="form-control localidad" />
							</div>
						</div>
						<div class="form-group row">
							<label for="municipio" class="col-sm-2 col-form-label">Municipio</label>
							<div class="col-sm-10">
								<form:input path="municipio" cssClass="form-control municipio" />
							</div>
						</div>
						<div class="form-group row">
							<label for="fechaBaja" class="col-sm-2 col-form-label">Fecha Baja</label>
							<div class="col-sm-10">
								<form:input path="fechaBaja" 
												data-date-format="mm/dd/yyyy"
												data-date-container='#editar'
												cssClass="form-control datepicker fecha_corta fechaBaja" 
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
	
	<div class="modal" id="nuevoDocumento" tabindex="-1" role="dialog" aria-labelledby="Nuevo Docmento" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Subir Logo</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarDocumento" cssClass="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id"/>
						
						<div class="form-group row">
							<label for="archivo" class="col-form-label col-md-3 text-nowrap">Logo:</label>
							<div class="col-md-9">
								<input type="file" name="archivo" class="archivo form-control"/>
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