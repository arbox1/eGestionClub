<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Actividades</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			var recargar = false;
			
			$('.buscador').on('click', '.accion', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('.buscador form').prop('action', data.accion).submit();
				
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
		<div class="row buscador">
			<div class="col-md-12">
				<form:form action="buscar" cssClass="form-horizontal" method="post" modelAttribute="buscador">
					<div class="row form-group">
						<label for="tipo" class="col-sm-1 col-form-label">Tipo</label>
						<div class="col-md-3">
							<form:select path="tipo.id" cssClass="form-control" >
								<form:option value="" label="--Selecciona un curso"/>
								<form:options items="${tipos}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-6 text-left">
						</div>
						<div class="col-md-6 text-right">
							<button type="button" class="btn btn-primary" data-limpiar=".buscador form">Limpiar</button>
							<button type="button" class="btn btn-primary accion" data-accion="buscar">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		
		<div class="row">
			<c:forEach var="tipo" items="${tipos}">
				<div class="col-md-<fmt:formatNumber value='${12 div tipos.size()}' maxFractionDigits='0'/>" data-id="${tipo.id}">
					<div class="cajaBoton" data-id="${actividad.id}">
						<span class="text-center">${tipo.descripcion}</span>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="row">
			<c:forEach var="actividad" items="${actividades}">
				<div class="col-md-<fmt:formatNumber value='${12 div actividades.size()}' maxFractionDigits='0'/>" data-id="${actividad.id}">
					<div class="cajaDato" data-id="${actividad.id}">
						<div class="row">
							<div class="col-md-12">
								<span class="text-center">${actividad.tipo.descripcion}</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<span class="text-center">${actividad.descripcion}</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<span class="text-center"><b>Plazo: </b><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${actividad.fechaFinPlazo}" /></span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<c:choose>
			<c:when test="${actividades.size() > 0}">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered extendida actividades" id="tablaActividades">
							<thead>
								<tr>
									<th class="text-center">Tipo</th>
									<th class="text-center">Descripcion</th>
									<th class="text-center">Estado</th>
									<th class="text-center">Lugar Salida</th>
									<th class="text-center">Precio</th>
									<th class="text-center">Participantes</th>
									<th class="text-center">Inscritos</th>
									<th class="text-center">Fecha Inicio</th>
									<th class="text-center">Fecha Fin</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="actividad" items="${actividades}">
									<tr>
										<td class="text-center">${actividad.tipo.descripcion}</td>
										<td>${actividad.descripcion}</td>
										<td class="text-center">${actividad.estado.descripcion}</td>
										<td>${actividad.lugarSalida}</td>
										<td class="text-center">${actividad.precio} &euro;</td>
										<td class="text-center">${actividad.participantes}</td>
										<td class="text-center">${actividad.inscritos}</td>
										<td class="text-center text-nowrap"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${actividad.fechaInicio}" /></td>
										<td class="text-center text-nowrap"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${actividad.fechaFin}" /></td>
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