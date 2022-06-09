<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>

<!-- 	<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script> -->
	<script src="https://cdn.tiny.cloud/1/rtk8mavs78p5cs96muejec1696x40orn8p8tqajk4grac3na/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

	<title>Noticias</title>
	
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
			
			$("#tablaNoticias").on("click", ".cargar", function(e){//Cargar
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	$('#editar').trigger("reload", data).mostrar();
		    }).on("click", ".eliminar", function(e){//Eliminar
		    	e.stopPropagation();
		    	var data = $(this).data();

		    	bootbox.confirm("¿Está seguro que desea eliminar la noticia?", function(result){
		    		if(result){
		    			$.enviarFormAjax(data.accion, {
		    				"id": data.id
		    			}, function (res){
		    				$('.buscador form').submit();
		    			});
		    		}
		    	});
		    });
			
			$('#editar').on("reload", function(e, data){
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#editar').cargarDatos({
		    			datos: res.resultados.noticia
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    }).on('show.bs.modal', function(e){
		    	e.stopPropagation();
		    	if($("#editar .fecha_corta").val() == '')
		    		$("#editar .fecha_corta").val(moment().format('DD/MM/YYYY'));
		    	
		    	if($("#editar .hora_corta").val() == '')
		    		$("#editar .hora_corta").val(moment().format('HH:mm'));
		    });
			
			$("#editar").on("click", ".guardar", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
// 		    	console.log($(new FormData($('#editar form')));
		    	
// 		    	$('#editar form').submit();
    			$('#editar form').enviar(data.accion, function (res){
    				$('.buscador form').submit();
    			});
		    });
			
			tinymce.init({
		        selector: '#miDescripcion'
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
						<label for="tipo" class="col-sm-1 col-form-label">Asunto</label>
						<div class="col-md-3">
							<form:input path="asunto" cssClass="form-control"/>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Descripción</label>
						<div class="col-sm-3">
							<form:input path="descripcion" cssClass="form-control"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nueva Noticia</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		
		<c:choose>
			<c:when test="${noticias.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida noticias" id="tablaNoticias">
							<thead>
								<tr>
									<th class="text-center">Asunto</th>
									<th class="text-center">Fecha</th>
									<th class="text-center">Visible</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="noticia" items="${noticias}">
									<tr>
										<td class="text-center">${noticia.asunto}</td>
										<td class="text-center text-nowrap"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${noticia.fecha}" /></td>
										<td class="text-center text-nowrap">${noticia.visible == "S" ? "Si" : "No"}</td>
										<td class="text-center text-nowrap">
											<button type="button" class="btn btn-link cargar"
												data-accion="cargar" data-id="${noticia.id}">
												<i class="fas fa-pencil-alt"></i>
											</button>
											<button type="button" class="btn btn-link eliminar"
												data-accion="eliminar" data-modelo="nuevo"
												data-id="${noticia.id}">
												<i class="fas fa-trash"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				No hay datos que mostrar
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="modal" id="editar" tabindex="-1" role="dialog" aria-labelledby="Editar Noticia" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Noticia</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo" enctype="multipart/form-data" acceptcharset="UTF-8">
						<input type="hidden" name="id" class="id no-limpiar"/>
						
						<div class="form-group row">
							<label for="asunto" class="col-sm-2 col-form-label">Asunto</label>
							<div class="col-sm-10">
								<input type="text" name="asunto" class="form-control asunto required" />
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="fecha" class="col-sm-4 col-form-label">Fecha</label>
									<div class="col-sm-8">
										<input type="text" name="fecha" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#editar'
											class="form-control datepicker fecha_corta fecha fechaValida required" 
											placeholder="dd/mm/aaaa"/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group row">
									<label for="hora" class="col-sm-4 col-form-label">Hora</label>
									<div class="col-sm-8">
										<input type="text" name="hora" 
											data-date-format="hh:mm"
											data-date-container='#editar'
											class="form-control hora_corta fecha required" 
											placeholder="HH:mm"/>
									</div>
								</div>
							</div>
						</div>
						
							
						<div class="form-group row">
							<label for="visible" class="col-sm-2 col-form-label">Visible</label>
							<div class="col-sm-10">
								<select name="visible" class="form-control visible required">
									<option value=""></option>
									<option value="S">Si</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="descripcion" class="col-sm-2 col-form-label">Descripcion</label>
							<div class="col-sm-10">
								<textarea id="miDescripcion" name="descripcion" class="form-control descripcion"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardar">Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>