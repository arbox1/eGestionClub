<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
%>
<!DOCTYPE html>
<html lang="es_ES">
<head><%@ page isELIgnored="false"%>

<title>eGestion: <sitemesh:write property='title' /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	var context = '${context}';
</script>

<link rel="shortcut icon" href="${context}resources/favicon.ico">

<!-- CSS -->
<link rel="stylesheet" href="${context}resources/css/bootstrap-datepicker.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/bootstrap-datepicker.min.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/bootstrap.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/animate.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/eGestion.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/datatable.css" type="text/css"/>
<script src="https://kit.fontawesome.com/16db5e2eb8.js" crossorigin="anonymous"></script>

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.4.0/bootbox.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.15/lodash.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-notify@3.1.3/bootstrap-notify.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-notify@3.1.3/bootstrap-notify.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment-with-locales.min.js "></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/async/3.2.0/async.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>

<script type="text/javascript" src="${context}resources/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${context}resources/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${context}resources/js/bootstrap-datepicker.es.min.js"></script>
<script type="text/javascript" src="${context}resources/js/utilidades.js"></script>
<script type="text/javascript" src="${context}resources/js/handlebars.js"></script>
<script type="text/javascript" src="${context}resources/js/autoNumeric.js"></script>
<script type="text/javascript" src="${context}resources/js/utilDataTable.js"></script>

<script src="https://cdn.datatables.net/buttons/1.6.5/js/dataTables.buttons.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.6.5/js/buttons.flash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.6.5/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.6.5/js/buttons.print.min.js "></script>

<script type="text/javascript">
	var abc='<spring:message code="message.badCredentials"/>';
	
	$( document ).ready(function() {
    	$.obtener(context+'base/menu', {
    		"id": 2
    	}, function(res){
 		    Handlebars.cargarPlantillas([
					'TRAMITES'
				], '').then(function(plantillas) {
					$('#menu').html(plantillas.tramites({
						"menu": res.resultados.menuEstructura,
						"context": context
					}));
			});
    	});

    	if('${param}' != '' && '${param}'=='{error=}'){
    		$.notify({
    			message: abc
    		}, {
    			type: "danger"
    		});
    	}
	});
	
	$("#login").on("click", ".aceptar", function(e){
    	e.stopPropagation();
    	var data = $(this).data();
		$('#editar form').enviar(data.accion, function (res){
   			$.enviarForm(data.accion, data.modelo, {
   				"id": data.id
   			});
			$('.buscador form').submit();
		});
    });
	
	$.notifyDefaults({
		placement: {
			from: "bottom",
			align: "center"
		},
		delay: 2500,
		timer: 500,
		z_index: 9999,
		animate: {
			enter: 'animated zoomInUp',
			exit: 'animated zoomOutDown'
		}
	});
</script>

<sitemesh:write property='head' />
</head>
<body>
	<header>
		<div class="container">
			<div class="row logado">
				<div class="col-sm-11">
				</div>
				<div class="col-sm-1 text-right" style="background-color: #dddddd; border-radius: 10px;">
					<button type="button" class="btn btn-link" data-toggle="modal" data-target="#login"><i class="fas fa-2x fa-sign-in-alt"></i></button>
				</div>
			</div>
		</div>
	</header>
	
	<div id="menu"></div>
	
	<h1 class='title'>
		<sitemesh:write property='title' />
	</h1>

	<div class='mainBody'>
		<sitemesh:write property='body' />
	</div>

	<div class='disclaimer'>
		<a href="mailto:atleticoalbaida@gmail.com"><i class="fas fa-solid fa-envelope"></i></a>
		<a href="https://www.facebook.com/atletico.albaida.7" target="_blank" ><i class="fa fa-brands fa-facebook"></i></a>
		<a href="https://instagram.com/atleticoalbaida" target="_blank" ><i class="fa fa-brands fa-instagram"></i></a>
		@ClubAtleticoAlbaida
		<c:if test="${param.error != null}">
		    <div id="error">
		        <spring:message code="message.badCredentials">   
		        </spring:message>
		    </div>
		</c:if>
	</div>

	<div class="modal" id="login" tabindex="-1" role="dialog" aria-labelledby="Editar Actividad" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Actividad</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="${context}login/logar2" class="form-horizontal validation form" modelAttribute="usuario" id="login" method="post">
						<div class="form-group row">
							<label for="tipo" class="col-sm-4 col-form-label">Usuario</label>
							<div class="col-sm-8">
								<input type="text" name="username" class="form-control username required" />
							</div>
						</div>
						<div class="form-group row">
							<label for="descripcion" class="col-sm-4 col-form-label">Contraseña</label>
							<div class="col-sm-8">
								<input type="password" name="password" class="form-control password required" />
							</div>
						</div>
						<div class="form-group text-right row">
							<div class="col-sm-12">
								
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-submit="#login.form">Aceptar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>