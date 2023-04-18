<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
	<title>Anular Reserva</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
			
			$('#reserva').on('click', '.guardar', function(e){
				e.stopPropagation();
				let data = $(this).data();
				
				bootbox.confirm("¿Está seguro que desea anular la reserva?", function(result){
		    		if(result){
		    			$('#reserva').enviar(data.accion, function(res){
							if(res.resultados.ok == 'S') {
								bootbox.alert("Reserva realizada correctamente", function(){ 
									$('#reserva').limpiar();
								});
							}
							
							$('#reserva').trigger("reload");
						});
		    		}
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
		<form action="anular" class="form-horizontal validation" method="post" modelAttribute="nuevo">
			<input type="hidden" name="hash" class="hash" value="${nuevo.hash}" />
			
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">Pista:</label>
				<div class="col-md-9">
					<span>${nuevo.pista.descripcion}</span>
				</div>
			</div>
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">Fecha:</label>
				<div class="col-md-9">
					<span><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${nuevo.fecha}" /></span>
				</div>
			</div>
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">Nombre:</label>
				<div class="col-md-9">
					<span>${nuevo.nombre}</span>
				</div>
			</div>
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">Correo:</label>
				<div class="col-md-9">
					<span>${nuevo.email}</span>
				</div>
			</div>
			<div class="form-group row">
				<label for="captcha" class="col-form-label col-md-3">Teléfono:</label>
				<div class="col-md-9">
					<span>${nuevo.telefono}</span>
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
				<button type="button" class="btn btn-primary guardar" data-accion="anular">Anular</button>
			</div>
		</div>
	</div>
</body>
</html>