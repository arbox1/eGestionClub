<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Actividades</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
			
			$("#datos").on("click", ".cajaBoton", function(e){//Cargar
		    	e.stopPropagation();
		    	var $data = $(this).data();
		    	
		    	$.obtener('actividades', {
		    		"id": $data.id
		    	}, function(res){
		 		    Handlebars.cargarPlantillas([
							'ACTIVIDADES.ACTIVIDADES'
						], '').then(function(plantillas) {
							$('#datos .actividades').html(plantillas.actividades({
								"actividades": res.resultados.actividades,
								"context": context
							}));
					});
		    	});
		    });
			
			$("#datos").on("click", ".cajaDato", function(e){//Cargar
		    	e.stopPropagation();
		    	var $data = $(this).data();
		    	
		    	$('#inscripcion .actividad_id').val($data.id);
		    	
		    	$('#inscripcion').mostrar();
		    });
			
			$('#inscripcion').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, ".id").val("");
		    	$(this, "form").limpiar();
		    });
			
			$('#inscripcion').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#inscripcion').enviar(data.accion, function(res){
					$('#inscripcion').modal("hide");
					recargar = true;
				});
			});
		});
	</script>
	
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row" id="datos">
			<div class="col-md-3">
				<c:forEach var="tipo" items="${tipos}">
					<div class="row">
						<div class="col-md-12 tipo">
							<div class="cajaBoton" data-id="${tipo.id}">
								<span class="text-center">${tipo.descripcion}</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="col-md-9 actividades">
			</div>
		</div>
	</div>
	
	<div class="modal" id="inscripcion" tabindex="-1" role="dialog" aria-labelledby="Participante a la actividad" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Participante</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardarParticipante" class="form-horizontal validation" method="post" modelAttribute="participante">
						<input type="hidden" name="id" class="id"/>
						<input type="hidden" name="actividad.id" class="actividad_id no-limpiar"/>
						<div class="form-group row">
							<label for="tipo" class="col-sm-3 col-form-label">Estado</label>
							<div class="col-sm-9">
								<select name="estado.id" class="form-control estado_id required">
									<option value=""></option>
									<c:forEach var="estado" items="${estadosParticipante}">
										<option value="${estado.id}">${estado.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="nombre" class="col-form-label col-md-3">Nombre:</label>
							<div class="col-md-9">
								<input type="text" name="nombre" class="form-control nombre required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="cantidad" class="col-form-label col-md-3">Cantidad:</label>
							<div class="col-md-9">
								<input type="text" name="cantidad" class="form-control cantidad required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="importe" class="col-form-label col-md-3">Importe:</label>
							<div class="col-md-9">
								<input type="text" name="importe" class="form-control importe noformat required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="pagado" class="col-form-label col-md-3">Pagado:</label>
							<div class="col-md-9">
								<select name="pagado" class="form-control pagado required">
									<option value=""></option>
									<option value="S">Si</option>
									<option value="N">No</option>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="telefono" class="col-form-label col-md-3">Teléfono:</label>
							<div class="col-md-9">
								<input type="text" name="telefono" class="form-control telefono required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="email" class="col-form-label col-md-3">Email:</label>
							<div class="col-md-9">
								<input type="text" name="email" class="form-control email required" placeholder="ejemplo@host.com" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="observacion" class="col-form-label col-md-3">Observaciones:</label>
							<div class="col-md-9">
								<textarea rows="4" name="observacion" class="form-control observacion"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarParticipante" >Guardar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>