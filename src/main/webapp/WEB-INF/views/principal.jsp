<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>BIENVENIDO</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
		});
	</script>
	
	<style type="text/css">
		.logo {
			    background-image: url(../resources/img/logo.png);
			    background-position: bottom right;
			    background-repeat: no-repeat;
			    background-size: contain;
			    margin-bottom: 1em;
			    padding-top: 0x;
			    height: 25em;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-sm-4">
			</div>
			<div class="col-sm-4 logo">
<!-- 				<img alt="Logo Club" src="../resources/img/logo.png" class="text-center" -->
<!-- 					height="200" width="200"> -->
			</div>
		</div>
	</div>
</body>