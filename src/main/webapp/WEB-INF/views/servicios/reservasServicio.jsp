<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
	<title>Reservas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
			
			$('#reserva .hora').select({
				accion: 'selectHora',
	    		blank: ' ',
	    		forceSingleSelection: true
	    	});
			
			$('#reserva .cargarHora').change(function(e){
				e.stopPropagation();
				var id = $('#reserva .pista_id').val();
				var fecha = $('#reserva .fecha').val();
				if(id != null && id != "" && fecha != null && fecha != '') {
					$('#reserva .hora').select("cargar", {
			    		"id": id,
			    		"fechaDesdeString": fecha
			    	});
				}
			});
			
			$('#reserva').on('click', '.guardar', function(e){
				e.stopPropagation();
				let data = $(this).data();
				$('#reserva').enviar(data.accion, function(res){
					if(res.resultados.ok == 'S') {
						bootbox.alert("Reserva realizada correctamente", function(){ 
							$('#reserva').limpiar();
						});
					}
					
					$('#reserva').trigger("reload");
				});
			}).on('reload', function(e){
				$.obtener("captcha", {
		    	}, function(res){
		    		$("#reserva .srcImg").attr("src",res.resultados.valor.realCaptcha);
		    		$("#reserva .hiddenCaptcha").val(res.resultados.valor.hiddenCaptcha);
		    	});
			});

			$('#reserva').trigger("reload");
		});
	</script>
	
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container" id="reserva">
		<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
			<div class="form-group row">
				<label for="pista.id" class="col-sm-2 col-form-label">Pistas</label>
				<div class="col-sm-10">
					<select name="pista.id" class="form-control pista_id cargarHora required" >
						<option value=""></option>
						<c:forEach var="pista" items="${pistas}">
							<option value="${pista.id}">${pista.descripcion}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="fecha" class="col-sm-2 col-form-label">Fecha</label>
				<div class="col-sm-10">
					<input type="text" name="fecha" 
							data-date-format="mm/dd/yyyy"
							data-date-container='#reserva'
							class="form-control datepicker fecha_corta fecha cargarHora required" 
							placeholder="dd/mm/aaaa"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="pista.id" class="col-sm-2 col-form-label">Hora</label>
				<div class="col-sm-10">
					<select name="hora" class="form-control hora required" >
						<option value=""></option>
					</select>
				</div>
			</div>
		
			<div class="form-group row">
				<label for="nombre" class="col-sm-2 col-form-label">Nombre:</label>
				<div class="col-sm-10">
					<input type="text" name="nombre" class="form-control nombre required"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="email" class="col-sm-2 col-form-label">Email:</label>
				<div class="col-sm-10">
					<input type="text" name="email" class="form-control email required" placeholder="ejemplo@host.com"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="telefono" class="col-sm-2 col-form-label">Teléfono:</label>
				<div class="col-sm-10">
					<input type="text" name="telefono" class="form-control telefono required"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">CAPTCHA:</label>
				<div class="col-md-4">
					<img src="" alt="Captcha" class="srcImg" />
				</div>
				<div class="col-md-5">
					<input type="hidden" name="hiddenCaptcha" class="form-control hiddenCaptcha required" />
					<input type="text" name="captcha" class="form-control captcha required" />
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-12 text-right">
				<button type="button" class="btn btn-primary" data-limpiar="#reserva form">Limpiar</button>
				<button type="button" class="btn btn-primary guardar" data-accion="guardar">Guardar</button>
			</div>
		</div>
	</div>
</body>
</html>