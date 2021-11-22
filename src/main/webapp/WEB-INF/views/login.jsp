<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
				<form action="logar2" class="form-horizontal validation" modelAttribute="usuario" id="form" method="post">
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
							<button type="button" class="btn btn-primary" data-submit="#form">Aceptar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>