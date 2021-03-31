<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Cuotas</title>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-body">
						<table class="table table-striped table-bordered socios dataTable no-footer" id="tablaSocios">
							<thead>
								<tr>
									<th class="text-center">Nombre</th>
<!-- 									<th></th> -->
									<c:forEach var="mes" items="${meses}">
										<th>${mes.codigo}</th>
									</c:forEach>
									<th>Total</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="socioCurso" items="${sociosCurso}">
									<tr>
										<td>${socioCurso.socio.nombreCompleto}</td>
										<c:forEach var="mes" items="${meses}">
											<td class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${socioCurso.cuota(mes.id).importe}"/> &euro;</td>
										</c:forEach>
										<td class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${socioCurso.total}"/> &euro;</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="12"></th>
									<th class="text-right text-nowrap"><fmt:formatNumber pattern="#,##0.00" value="${totales}"/> &euro;</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>