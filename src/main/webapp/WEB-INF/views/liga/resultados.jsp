<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
	
	<title>Ligas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			$('.buscador').on('click', '.accion', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$('.buscador form').prop('action', data.accion).submit();
			});
			
			$('#liga').on('click', '.resultado', function(e){
				e.stopPropagation();
				var data = $(this).data();
				
				$.obtener(data.accion, {
		    		"id": data.calendario
		    	}, function(res){
		    		Handlebars.cargarPlantillas([
						'LIGAS.CALENDARIO'
					], '').then(function(plantillas) {
						$('#calendario .datos').html(plantillas.calendario({
							"calendario": res.resultados.calendario,
							"context": context
						}));
						
						$('#calendario').modal("show");
					});
		    	});
			});
			
			$('#calendario').on('hide.bs.modal', function(e){
				e.stopPropagation();
				
				$('#calendario .div').html("");
			});
			
			$('#calendario').on('click', '.guardar', function(e){
				e.stopPropagation();
				var data = $(this).data();
				$('#calendario form').enviar(data.accion, function(res){
					if(res.resultados.ok == 'S'){
						var resultado = res.resultados.resultado;
						if(resultado.resultadoa != null && resultado.resultadob != null) {
							$('#liga .resultado_'+resultado.id).html(resultado.resultadoa + ' - ' + resultado.resultadob);
						} else {
							$('#liga .resultado_'+resultado.id).html(' - ');
						}
						$('#calendario').modal("hide");	
					}
					recargar = true;
				});
			});
			
			$("#tablaClasificacion").on("click", ".resultadosEquipo", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	
		    	$('#resultadosEquipo').trigger("reload", data).mostrar();
		    });
			
			$('#resultadosEquipo').on("reload", function(e, data){
				e.stopPropagation();
				$('#resultadosEquipo .modal-title').html(data.titulo);
				
		    	$.obtener(data.accion, {
		    		"id": data.id
		    	}, function(res){
		    		Handlebars.cargarPlantillas([
						'LIGAS.RESUMEN'
					], '').then(function(plantillas) {
						$('#resultadosEquipo .resumen').html(plantillas.resumen({
							"resultados": res.resultados.resultados,
							"context": context
						}));
					});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    });
		});
	</script>
	
	<style type="text/css">
		#liga div.jornada {
			background-color: #4582EC;
			color: white;
			font-weight: bold;
		}
		
		#liga div.calendario {
			border-color: #4582EC;
			border-width: 1px;
			border: solid;
		}
		
 		#liga div.as:nth-child(2n) {
/*  			background-color: black;  */
 		} 
 		
 		#liga div.resultado:nth-child(2n-1) {
 			background-color: rgba(0, 0, 0, 0.05); 
 			cursor: pointer;
 		} 
 		
 		#resultadosEquipo div.resultado:nth-child(2n-1) {
 			background-color: rgba(0, 0, 0, 0.05); 
 			cursor: pointer;
 		} 
		
		#resultadosEquipo div.jornada {
			background-color: #4582EC;
			color: white;
			font-weight: bold;
		}
 		
 		#resultadosEquipo div.calendario {
			border-color: #4582EC;
			border-width: 1px;
			border: solid;
		}
		
		#resultadosEquipo div.red {
			background-color: red;
			color: white;
		}
		
		#resultadosEquipo div.green {
			background-color: green;
			color: white;
		}
		
		#resultadosEquipo div.black {
			background-color: black;
			color: white;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row buscador">
			<div class="col-md-12">
				<form:form action="buscar" cssClass="form-horizontal" method="post" modelAttribute="buscador">
					<div class="row form-group">
						<label for="liga" class="col-sm-1 col-form-label">Liga</label>
						<div class="col-md-3">
							<form:select path="id" cssClass="form-control" >
								<form:option value="" label="--Selecciona una liga"/>
								<form:options items="${ligas}" itemValue="id" itemLabel="descripcion"/>
							</form:select>
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
		<div class="row buscador">
			<div class="col-md-12">
				<c:choose>
					<c:when test="${grupos.size() > 0}">
						<div class="panel panel-info" id="liga">
							<div class="panel-body">
								<c:forEach items="${grupos}" var="grupo">
									<div class="clasificacion">
										<div class="row">
											<div class="col-md-12 text-center font-weight-bold">
												Clasificacion
											</div>
										</div>
										<div class="panel panel-info">
											<div class="panel-body">
												<table class="table table-striped table-bordered table-hover extendida clasificacion" id="tablaClasificacion">
													<thead>
														<tr>
															<th class="text-center">Pos.</th>
															<th class="text-center">Equipo</th>
															<th class="text-center">Puntos</th>
															<th class="text-center">PJ</th>
															<th class="text-center">PG</th>
															<th class="text-center">PE</th>
															<th class="text-center">PP</th>
															<th class="text-center">PNP</th>
															<th class="text-center">TF</th>
															<th class="text-center">TC</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="puesto" items="${grupo.puestos}" varStatus="pos">
															<tr>
																<td class="text-center">${pos.index+1}</td>
																<td class="">${puesto.equipo}</td>
																<td class="text-center">${puesto.puntos}</td>
																<td class="text-center">${puesto.encuentrosJugados}</td>
																<td class="text-center">${puesto.encuentrosGanados}</td>
																<td class="text-center">${puesto.encuentrosEmpatados}</td>
																<td class="text-center">${puesto.encuentrosPerdidos}</td>
																<td class="text-center">${puesto.encuentrosNoPresentados}</td>
																<td class="text-center">${puesto.tantosFavor}</td>
																<td class="text-center">${puesto.tantosContra}</td>
																<td class="text-center text-nowrap">
																	<button type="button" class="btn btn-link resultadosEquipo"
																		data-accion="resultadosEquipo" data-id="${puesto.idEquipo}" data-titulo="Resultados de ${puesto.equipo}">
																		<i class="fas fa-eye"></i>
																	</button>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="partidos">
										<div class="row">
											<div class="col-md-12 text-center font-weight-bold">
												${grupo.descripcion}
											</div>
										</div>
										<c:forEach items="${grupo.jornadas}" var="jornada" varStatus="j">
											<div class="row">
												<div class="col-md-12 jornada">
													Jornada ${jornada.numero}
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 calendario">
													<c:forEach items="${jornada.calendarios}" var="calendario" varStatus="i">
														<div class="row resultado" data-calendario="${calendario.id}" data-accion="cargar">
															<div class="col-md-4 resultado" data-calendario="${calendario.id}" data-accion="cargar">
																${calendario.equipoa.descripcion}
															</div>
															<div class="col-md-4 text-center resultado" data-calendario="${calendario.id}" data-accion="cargar">
																<c:forEach items="${calendario.resultados}" var="resultado">
																	<div class="resultado_${resultado.id}">
																		${resultado.resultadoa} - ${resultado.resultadob}
																	</div>
																</c:forEach>
															</div>
															<div class="col-md-4 resultado" data-calendario="${calendario.id}" data-accion="cargar">
																${calendario.equipob.descripcion}
															</div>
														</div>
													</c:forEach>
												</div>
											</div>
										</c:forEach>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						No hay datos que mostrar
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	
	<div class="modal" id="calendario" tabindex="-1" role="dialog" aria-labelledby="Editar Liga" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Resultado</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="datos"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
					<button type="button" class="btn btn-primary" data-limpiar="#editar form">Limpiar</button>
					<button type="button" class="btn btn-primary guardar" data-accion="guardarResultado">Guardar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="resultadosEquipo" tabindex="-1" role="dialog" aria-labelledby="Resultados Equipos" aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Resultados</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-body">
							<div class="resumen"></div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>