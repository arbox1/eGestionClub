<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>ERROR de la aplicaci�n</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row buscador">
			<div class="col-sm-4 text-center">
			</div>
			<div class="col-sm-4 text-center">
				Se ha producido un error en la aplicaci�n. El c�digo de error es ${error}. Contacte con el Administrador para solucionar el problema.
			</div>
		</div>
	</div>
</body>
</html>