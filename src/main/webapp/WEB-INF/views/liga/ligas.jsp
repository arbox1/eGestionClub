<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Ligas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row buscador">
			<div class="col-md-12">
				<form:form action="buscar" cssClass="form-horizontal" method="post" modelAttribute="buscador">
					<div class="row form-group">
						<label for="tipo" class="col-sm-1 col-form-label">Estado</label>
						<div class="col-md-3">
							<form:select path="estado.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona estado"/>
								<form:options items="${estados}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-3">
							<form:select path="tipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
						<label for="descripcion" class="col-sm-1 col-form-label">Descripción</label>
						<div class="col-sm-3">
							<form:input path="descripcion" cssClass="form-control"/>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editar">Nueva Liga</button>
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<c:choose>
			<c:when test="${ligas.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered table-hover extendida ligas" id="tablaLigas">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Estado</th>
									<th class="text-center">Lugar</th>
									<th class="text-center">Grupos</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="liga" items="${ligas}">
									<tr>
										<td class="text-center">${liga.tipo.descripcion}</td>
										<td>${liga.descripcion}</td>
										<td class="text-center">${liga.estado.descripcion}</td>
										<td>${liga.lugar}</td>
										<td class="text-center">0</td>
										<td class="text-center text-nowrap">
											
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				No hay datos que mostrar
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>