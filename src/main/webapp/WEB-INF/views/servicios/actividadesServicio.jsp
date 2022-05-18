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
		    		var result = res.resultados.actividades;
		    		result = result.map((act) => {
		    			  act.fechaFinPlazo = moment(act.fechaFinPlazo).format('DD/MM/YYYY');
		    			  return act;
		    			});
		 		    Handlebars.cargarPlantillas([
							'ACTIVIDADES.ACTIVIDADES'
						], '').then(function(plantillas) {
							$('#datos .actividades').html(plantillas.actividades({
								"actividades": result,
								"context": context
							}));
					});
		 		    
		 		    
		    	});
		    }).on("click", ".inscribir", function(e){//Cargar
		    	e.stopPropagation();
		    	var $data = $(this).data();
		    	
		    	$('#inscripcion .actividad_id').val($data.id);
		    	
		    	$.obtener("captcha", {
		    	}, function(res){
		    		$("#inscripcion .srcImg").attr("src",res.resultados.valor.realCaptcha);
		    		$('#inscripcion form').cargarDatos({
		    			datos: res.resultados.valor
		    		});
		    	});
		    	
		    	$('#inscripcion').mostrar();
		    }).on("click", ".consulta", function(e){//Cargar
		    	e.stopPropagation();
		    	var $data = $(this).data();
		    	
		    	$('#consulta .actividad_id').val($data.id);
		    	
		    	$.obtener("captcha", {
		    	}, function(res){
		    		$("#consulta .srcImg").attr("src",res.resultados.valor.realCaptcha);
		    		$('#consulta form').cargarDatos({
		    			datos: res.resultados.valor
		    		});
		    	});
		    	
		    	$('#consulta').mostrar();
		    }).on("click", ".documentos", function(e){//Cargar
		    	e.stopPropagation();
		    	var $data = $(this).data();
		    	
		    	$('#documentos').trigger("reload", $data).mostrar();
		    });
			
			$('#inscripcion').on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$(this, "form").limpiar();
		    });
			
			$('#inscripcion').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#inscripcion').enviar(data.accion, function(res){
					if(res.resultados.ok == 'S'){
						$('#inscripcion').modal("hide");
					} else {
						$('#inscripcion').cargarDatos({
			    			datos: res.resultados.valor
			    		});
					}
				});
			});
			
			$('#consulta').on('click', '.consultar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('#consulta form').enviar(data.accion, function(res){
					$("#consulta .srcImg").attr("src",res.resultados.valor.realCaptcha);
		    		if(res.resultados.ok == 'S'){
						$("#consulta .datos").removeClass('d-none').addClass('d-block');
						$('#consulta .estado_descripcion').html(res.resultados.participante.estado.descripcion);
						$('#consulta').cargarDatos({
			    			datos: res.resultados.participante
			    		});
						$('#consulta form').cargarDatos({
			    			datos: res.resultados.valor
			    		});
					} else {
						$("#consulta .datos").removeClass('d-block').addClass('d-none');
						$('#consulta form').cargarDatos({
			    			datos: res.resultados.valor
			    		});
					}
				});
			}).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	
		    	$("#consulta .datos").removeClass('d-block').addClass('d-none');
		    	$(this, "form").limpiar();
		    });
			
			$('#documentos table.detalle').DataTable({
				language: {
					"emptyTable": "No hay documentos de esta actividad"
				},
				"paging": false,
				"searching": false,
				"info":     false,
				"buttons": [],
    			"columns": [
//     				{ data: "documento.tipo.descripcion", title: "Tipo" },
    	            { data: "documento.descripcion", title: "Descripcion" },
    	            { data: "documento.mime", title: "Mime" },
    	            { data: function ( row, type, val, meta ) {
						var $buttons = $('<p>').addBoton({
								tipo: 'link',
								icono: 'search',
								clases: 'descargar',
								title: 'Descargar',
								data: {
									id: row.documento.id,
									accion: 'documento'
								}
							});
							return $.toHtml($buttons);
						}, 
						title: "",
						className: 'text-nowrap text-center'
	    	    	}
    	        ]
    		});
			
			$('#documentos').on("reload", function(e, data){
				e.stopPropagation();
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		$('#documentos table.detalle').reloadTable(res.resultados.documento);
		    	});
		    }).on('click', '.descargar', function(e){
				e.stopPropagation();
				var $data = $(this).data();
				$.enviarForm($data.accion, "valor", {
    				"id": $data.id
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
		<div class="modal-dialog modal-lg" role="document">
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
						<input type="hidden" name="actividad.id" class="actividad_id no-limpiar"/>
						<div class="form-group row">
							<label for="nombre" class="col-form-label col-md-3">Nombre:</label>
							<div class="col-md-9">
								<input type="text" name="nombre" class="form-control nombre required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="dni" class="col-form-label col-md-3">DNI:</label>
							<div class="col-md-9">
								<input type="text" name="dni" class="form-control dni dniValido" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="fechaNacimiento" class="col-form-label col-md-3">F. Nacimiento:</label>
							<div class="col-md-9">
								<input type="text" name="fechaNacimiento" 
											data-date-format="mm/dd/yyyy"
											data-date-container='#inscripcion'
											class="form-control datepicker fecha_corta fechaNacimiento fechaValida" 
											placeholder="dd/mm/aaaa"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="cantidad" class="col-form-label col-md-3">Nº Participantes:</label>
							<div class="col-md-9">
								<input type="text" name="cantidad" class="form-control cantidad required" />
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
								<textarea rows="4" name="observacion" maxlength="4000" class="form-control observacion"></textarea>
							</div>
						</div>
						<div class="form-group row">
							<label for="captcha" class="col-form-label col-md-3">CAPTCHA:</label>
							<div class="col-md-4">
								<img src="" alt="Captcha" class="srcImg" />
							</div>
							<div class="col-md-5">
								<input type="hidden" name="hiddenCaptcha" class="form-control hiddenCaptcha required" />
								<input type="text" name="captcha" class="form-control captcha" />
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
	
	<div class="modal" id="consulta" tabindex="-1" role="dialog" aria-labelledby="Participante" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Participante</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="consultar" class="form-horizontal validation" method="post" modelAttribute="participante">
						<input type="hidden" name="id" class="actividad_id no-limpiar"/>
						<div class="form-group row">
							<label for="nombre" class="col-form-label col-md-2">Usuario:</label>
							<div class="col-md-10">
								<input type="text" name="nombre" class="form-control nomb required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="dni" class="col-form-label col-md-2">Password:</label>
							<div class="col-md-10">
								<input type="password" name="password" class="form-control pass required" />
							</div>
						</div>
						
						<div class="form-group row">
							<label for="captcha" class="col-form-label col-md-2">CAPTCHA:</label>
							<div class="col-md-4">
								<img src="" alt="Captcha" class="srcImg" />
							</div>
							<div class="col-md-6">
								<input type="hidden" name="hiddenCaptcha" class="form-control hiddenCaptcha required" />
								<input type="text" name="captcha" class="form-control captcha" />
							</div>
						</div>
					</form>
					
					<div class="datos d-none">
						<div class="row">
							<label for="nombre" class="font-weight-bold col-md-2">Estado:</label>
							<span class="estado_descripcion font-weight-bold col-md-10"></span>
						</div>
						<div class="row">
							<label for="nombre" class="font-weight-bold col-md-2">Nombre:</label>
							<span class="nombre col-md-10"></span>
						</div>
						<div class="row">
							<label for="dni" class="font-weight-bold col-md-2">DNI:</label>
							<span class="dni col-md-10"></span>
						</div>
						<div class="row">
							<label for="dni" class="font-weight-bold col-md-2">Fecha Nac.:</label>
							<span class="fechaNacimiento fecha_corta_span col-md-3"></span>
							<label for="participantes" class="font-weight-bold col-md-2">Importe:</label>
							<span class="importe col-md-5"></span>
						</div>
						<div class="row">
							<label for="participantes" class="font-weight-bold col-md-2 text-nowrap">Nº Participantes:</label>
							<span class="cantidad col-md-3"></span>
							<label for="participantes" class="font-weight-bold col-md-2">Pagado:</label>
							<span class="pagado sino col-md-5"></span>
						</div>
						<div class="row">
							<label for="telefono" class="font-weight-bold col-md-2">Teléfono:</label>
							<span class="telefono col-md-3"></span>
							<label for="email" class="font-weight-bold col-md-2">DNI:</label>
							<span class="email col-md-5"></span>
						</div>
						<div class="row">
							<label for="observacion" class="font-weight-bold col-md-2">Observaciones:</label>
							<span class="observacion col-md-10"></span>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary consultar" data-accion="consultar" >Aceptar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="documentos" tabindex="-1" role="dialog" aria-labelledby="Documentos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Documentos</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row botonera">
					</div>
					<table class="table table-striped table-bordered dataTable no-footer detalle">
					</table>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>