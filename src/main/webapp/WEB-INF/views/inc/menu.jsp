<div class="container">
	<div class="row">
	</div>
	<div class="col-md-offset-1 col-md-10">
		<c:forEach var="estructura" items="${menuEstructura}">
			<div class="btn-group">
				<div class="dropdown">
					<button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${estructura.descripcion}</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<c:forEach var="menu" items="${estructura.menus}">
							<c:url var="pagina" value="${menu.pagina}" />
							<a class="dropdown-item" href="${pagina}">${menu.descripcion}</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>