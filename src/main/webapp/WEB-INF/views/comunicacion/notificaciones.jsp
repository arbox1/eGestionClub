<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Cuotas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
			$('#recordatorio').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#recordatorio').enviar(data.accion, function(res){
					$('#recordatorio').modal("hide");
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
		<div class="row">
			<div class="col-sm-12">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#recordatorio">Enviar recordatorio cuotas</button>
			</div>
		</div>
	</div>
	
	<div class="modal" id="recordatorio" tabindex="-1" role="dialog" aria-labelledby="Editar Cuota" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Correo recordatorio</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="recordatorio" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group row">
									<label for="importe" class="col-sm-4 col-form-label">Curso:</label>
									<div class="col-sm-8">
										<select name="id" class="form-control required">
											<option value=""></option>
											<c:forEach var="curso" items="${cursos}">
												<option value="${curso.id}">${curso.descripcion}</option>
											</c:forEach>
										</select>
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
							<button type="button" class="btn btn-primary btn-fv-submit guardar" data-accion="recordatorio">Enviar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>