<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Acceso eGestion</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="offset-sm-4 col-sm-4">
				<form:form action="logar2" cssClass="form-horizontal validation" modelAttribute="usuario" id="form">
					<div class="form-group row">
						<label for="tipo" class="col-sm-4 col-form-label">Usuario</label>
						<div class="col-sm-8">
							<form:input path="username" cssClass="form-control username required" />
						</div>
					</div>
					<div class="form-group row">
						<label for="descripcion" class="col-sm-4 col-form-label">Contraseña</label>
						<div class="col-sm-8">
							<form:password path="password" cssClass="form-control password required"/>
						</div>
					</div>
					<div class="form-group text-right row">
						<div class="col-sm-12">
							<button type="button" class="btn btn-primary" data-submit="#form">Aceptar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>