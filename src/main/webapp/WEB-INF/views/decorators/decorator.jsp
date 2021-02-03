<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>

<title>eGestion: <sitemesh:write property='title' /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	var context = '${context}';
</script>

<link rel="shortcut icon" href="${context}resources/favicon.ico">

<!-- CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.css"/> -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"/>
 
<link rel="stylesheet" href="${context}resources/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/bootstrap.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/animate.css" type="text/css"/>
<link rel="stylesheet" href="${context}resources/css/eGestion.css" type="text/css"/>
<script src="https://kit.fontawesome.com/16db5e2eb8.js" crossorigin="anonymous"></script>

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.4.0/bootbox.min.js"></script>
<!-- <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.js"></script> -->
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.15/lodash.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-notify@3.1.3/bootstrap-notify.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-notify@3.1.3/bootstrap-notify.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.es.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment-with-locales.min.js "></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/async/3.2.0/async.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>
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
	$( document ).ready(function() {
    	$.obtener(context+'base/menu', {
    		"id": 1
    	}, function(res){
 		    Handlebars.cargarPlantillas([
					'MENU'
				], '').then(function(plantillas) {
					$('#menu').html(plantillas.menu({
						"menu": res.resultados.menuEstructura,
						"context": context
					}));
			});
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
<!-- 			<h1>CD Atletico Albadida</h1> -->
<!-- 			<h2>CONTRATOS ISE</h2> -->
		</div>
	</header>
	<div id="menu"></div>
	<h1 class='title'>
		<sitemesh:write property='title' />
	</h1>

	<div class='mainBody'>
		<sitemesh:write property='body' />
	</div>

	<div class='disclaimer'>Emilio López Delgado</div>

</body>
</html>