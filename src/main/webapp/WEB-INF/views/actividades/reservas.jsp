<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head><%@ page isELIgnored="false"%>
<title>Reservas</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

	<script type="text/javascript">
		var dia = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];
		const Calendar = tui.Calendar;

		$( document ).ready(function() {
			const calendar = new Calendar('#calendarMio', {
				defaultView: 'week',
				useFormPopup: false,
				useDetailPopup: false,
				week: {
					startDayOfWeek: 1,
					dayNames: dia,
					hourStart: 9,
					weekend: {
				    	backgroundColor: '#FA6C6C'
					},
					hourEnd: 23,
					eventView: ['time'],
					taskView: false
// 					scheduleView: ['time']
				},
				month: {
					startDayOfWeek: 1,
					dayNames: dia
				},
				timezone: {
			    	zones: [
			      		{
			        		timezoneName: 'Europe/Madrid',
			           		displayLabel: 'España',
			            	tooltip: 'Hora España'
			      		}
			    	]
			  	},
			  	gridSelection: {
			  	    enableDblClick: false,
			  		enableClick: true,
			  	},
				template: {
					time(event) {
					  var horaString = String(event.start.getHours()).padStart(2, '0') + ':' + String(event.start.getMinutes()).padStart(2, '0');
				      return '<span style="color: black;">'+event.title+'</span><br/><span style="color: black;">'+event.body+'</span><br/><span style="color: black;">'+horaString+'</span>';
				    },
				},
				theme: {
					week: {
					    timeGridLeftAdditionalTimezone: {
					      backgroundColor: '#e5e5e5'
					    }
					  }
				},
				calendars: [
					{
				    	id: 'cal1',
				      	name: 'Personal',
				    },
				    {
				      	id: 'cal2',
				      	name: 'Work',
				    },
				],
			});
			
			calendar.setOptions({
				template: {
			    	timegridDisplayPrimaryTime({ time }) {
			    		return moment(time.d.d).format('HH:mm');
			    	}
			  	}
			});
			
			$('#calendarMio').on('click', '.toastui-calendar-event-time', function(e) {
				var data = $(this).data();
				
				if (data && data.eventId > 0) {
					$('#reserva').trigger('reload', data).mostrar();
				}
			});
			
			$('#botonera').on("click", ".changeView", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	calendar.changeView(data.valor);
		    	calendar.render();
		    	$('#calendarMio').trigger("reload");
		    }).on("click", ".offset", function(e){
		    	e.stopPropagation();
		    	var data = $(this).data();
		    	if (data.valor === -1) {
	    			calendar.prev();
	    		} else if (data.valor === 1) {
	    			calendar.next();
	    		}	
		    	$('#calendarMio').trigger("reload");
		    }).on("click", ".refrescar", function(e){
		    	e.stopPropagation();
		    	$('#calendarMio').trigger("reload");
		    });
			
			calendar.on('beforeUpdateEvent', function ({ event, changes }) {
			  const { id, calendarId } = event;

			  calendar.updateEvent(id, calendarId, changes);
			});
			
			$('#calendarMio').on("reload", function(e){
				e.stopPropagation();
				let fechaInicio = new Date(calendar.getDateRangeStart());
				let fechaFin = new Date(calendar.getDateRangeEnd());
				let texto = moment(fechaInicio).format('DD/MM/YYYY')+ " - " +moment(fechaFin).format('DD/MM/YYYY');
				$('#botonera .RANGO').html(texto);
				
				$.obtener('cargar', {
		    		"fechaDesde": fechaInicio,
		    		"fechaHasta": fechaFin
		    	}, function(res){
		    		calendar.clear();
		    		$.each(res.resultados.reservas, function( index, value ) {
		    			let fechaInicio = moment(value.fecha);
		    			let fechaFin = moment(value.fecha).add(1, 'h').add(30, 'm');
		    			calendar.createEvents([
							  {
							    id: value.id,
							    calendarId: '1',
							    title: value.pista.descripcion,
							    category: 'time',
							    dueDateClass: '',
							    start: fechaInicio,
							    end: fechaFin,
							    body: value.nombre,
							    backgroundColor: value.pista.color
							  }
						]);
	    			});
		    		
		    		$.each(res.resultados.bloqueos, function( index, value ) {
		    			let fechaInicio = moment(value.fechaDesde);
		    			let fechaFin = moment(value.fechaHasta);
		    			calendar.createEvents([
							  {
							    id: -1*value.id,
							    calendarId: '1',
							    title: value.pista.descripcion,
							    category: 'time',
							    dueDateClass: '',
							    start: fechaInicio,
							    end: fechaFin,
							    body: 'Bloqueo',
							    backgroundColor: '#FA6C6C'
							  }
						]);
	    			});
		    	});
			});
			
			$('#calendarMio').trigger("reload");
			
			$('#reserva .hora').select({
				accion: 'selectHora',
	    		blank: ' ',
	    		forceSingleSelection: true
	    	});
			
			$('#reserva .cargarHora').change(function(e){
				e.stopPropagation();
				var id = $('#reserva .pista_id').val();
				var fecha = $('#reserva .fecha').val();
				if(id != null && id != "" && fecha != null && fecha != '') {
					$('#reserva .hora').select("cargar", {
			    		"id": id,
			    		"fechaDesdeString": fecha
			    	});
				}
			});
			
			$('#reserva').on("reload", function(e, data){
				e.stopPropagation();
				
		    	$.obtener('cargarReserva', {
		    		"id": data.eventId
		    	}, function(res){
		    		$('#reserva').cargarDatos({
		    			datos: res.resultados.reserva
		    		});
		    	});
		    }).on('hide.bs.modal', function(e){
		    	e.stopPropagation();
		    	$(this, "form").limpiar();
		    }).on("click", '.eliminar', function(e){
		    	e.stopPropagation();
		    	let id = $('#reserva form .id').val();
		    	let data = $(this).data();
		    	
		    	if(id != null && id != '') {
					bootbox.confirm("¿Está seguro que desea eliminar la reserva?", function(result){
			    		if(result){
			    			$.enviarFormAjax(data.accion, {
			    				"id": id
			    			}, function(res){
			    				$('#calendarMio').trigger("reload");
			    				$('#reserva').modal("hide");
			    			});
			    		}
			    	});
		    	}
		    }).on('click', '.guardar', function(e){
				e.stopPropagation();
				let data = $(this).data();
				$('#reserva').enviar(data.accion, function(res){
					$('#calendarMio').trigger("reload");
					$('#reserva').modal("hide");
				});
			});
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/mensajes.jsp"%>

	<div class="row" id="botonera" style="margin-bottom: 10px;">
		<div class="col-md-4 text-left">
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#reserva">Nueva Reserva</button>
			<button type="button" class="btn btn-link refrescar" title="Refrescar"><i class="fas fa-sync"></i></button>
			<button type="button" class="btn btn-link changeView" data-valor="week" title="Semanal"><i class="fas fa-calendar-week"></i></button>
			<button type="button" class="btn btn-link changeView" data-valor="day" title="Diario"><i class="fas fa-calendar-alt"></i></button>
		</div>
		<div class="col-md-4 text-center">
			<button type="button" class="btn btn-link offset" data-valor="-1" title="Anterior"><i class="fas fa-angle-double-left"></i></button>
			<button type="button" class="btn btn-link offset" data-valor="1" title="Posterior"><i class="fas fa-angle-double-right"></i>
		</div>
		<div class="col-md-3 text-right">
			<span class="RANGO" style="font-weight: bold;"></span>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 text-center">
			<div id="calendarMio" style="height: 500px;"></div>
		</div>
	</div>
	
	<div class="modal" id="reserva" tabindex="-1" role="dialog" aria-labelledby="Reservar" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Reserva</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="guardar" class="form-horizontal validation" method="post" modelAttribute="nuevo">
						<input type="hidden" name="id" class="id no-limpiar"/>

						<div class="form-group row">
							<label for="pista.id" class="col-sm-2 col-form-label">Pistas</label>
							<div class="col-sm-10">
								<select name="pista.id" class="form-control pista_id cargarHora required" >
									<option value=""></option>
									<c:forEach var="pista" items="${pistas}">
										<option value="${pista.id}">${pista.descripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="fecha" class="col-sm-2 col-form-label">Fecha</label>
							<div class="col-sm-10">
								<input type="text" name="fecha" 
										data-date-format="mm/dd/yyyy"
										data-date-container='#reserva'
										class="form-control datepicker fecha_corta fecha cargarHora required" 
										placeholder="dd/mm/aaaa"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="pista.id" class="col-sm-2 col-form-label">Hora</label>
							<div class="col-sm-10">
								<select name="hora" class="form-control hora required" >
									<option value=""></option>
								</select>
							</div>
						</div>
					
						<div class="form-group row">
							<label for="nombre" class="col-sm-2 col-form-label">Nombre:</label>
							<div class="col-sm-10">
								<input type="text" name="nombre" class="form-control nombre required"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="email" class="col-sm-2 col-form-label">Email:</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control email required" placeholder="ejemplo@host.com"/>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="telefono" class="col-sm-2 col-form-label">Teléfono:</label>
							<div class="col-sm-10">
								<input type="text" name="telefono" class="form-control telefono required"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-md-12 text-right">
							<button type="button" class="btn btn-primary eliminar" data-accion="eliminar" data-modelo="nuevo">Eliminar</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
							<button type="button" class="btn btn-primary" data-limpiar="#reserva form">Limpiar</button>
							<button type="button" class="btn btn-primary guardar" data-accion="guardar">Guardar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>