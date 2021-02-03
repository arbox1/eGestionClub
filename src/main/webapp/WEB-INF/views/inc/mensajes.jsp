<c:forEach var="mensaje" items="${mensajes}">
	<script type="text/javascript">
		$.notify({
			message: '<c:out value="${mensaje.mensaje}"/>'
		},{
			type: '<c:out value="${mensaje.tipo}"/>'
		});
	</script>
</c:forEach>