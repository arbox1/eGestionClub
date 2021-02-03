<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Usuarios</title>

<script type="text/javascript">
	$( document ).ready(function() {
    	
	});
</script>

</head>
<body>
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
	<div class="container">
		<div class="row">
			<div class="col-md-offset-1 col-md-10">
				<hr />
				<input type="button" value="Add Customer"
					onclick="window.location.href='showForm'; return false;"
					class="btn btn-primary" /> <br />
				<br />
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">Customer List</div>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-bordered">
							<tr>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Action</th>
							</tr>
	
							<!-- loop over and print our customers -->
							<c:forEach var="tempCustomer" items="${customers}">
	
								<!-- construct an "update" link with customer id -->
								<c:url var="updateLink" value="/customer/updateForm">
									<c:param name="customerId" value="${tempCustomer.id}" />
								</c:url>
	
								<!-- construct an "delete" link with customer id -->
								<c:url var="deleteLink" value="/customer/delete">
									<c:param name="customerId" value="${tempCustomer.id}" />
								</c:url>
	
								<tr>
									<td>${tempCustomer.firstName}</td>
									<td>${tempCustomer.lastName}</td>
									<td>${tempCustomer.email}</td>
									<td>
										<!-- display the update link --> <a href="${updateLink}">Update</a>
										| <a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>