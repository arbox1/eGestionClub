var ventana=null;

(function($) {
	var HTML_LOADING = Handlebars.compile(
			'<div class="modal" data-backdrop="static">' +
			'	<div class="modal-dialog modal-dialog-centered modal-sm">' +
			'		<div class="modal-content">' +
			'        	<div class="modal-body">' +
			'		      	<div class="row">' +
			'		      		<div class="col-sm-2 text-center">' +
			'		      			<span class="fa fa-spinner fa-spin fa-5x"></span>' +
			'		      		</div>' +
			'		      		<div class="col-sm-10">' +
			'		      			<h2 class="mensaje"></h2>' +
			'		      		</div>' +
			'		      	</div>' +
			'			</div>' +
			'		</div>' +
			'	</div>' +
			'</div>'
		);
	
	// datetimepicker: Establecer valores por defecto comunes a todos los datepicker
	/*
	$.fn.datepicker.defaults = $.extend({}, $.fn.datepicker.defaults, {
		format: 'dd/mm/yyyy', 
		language: 'es',
		autoclose: true
	});
	console.log($.fn.datepicker.defaults);
	*/
/*	
	$.fn.datetimepicker.defaults = $.extend({}, $.fn.datetimepicker.defaults, {
		locale: 'es',
		format: 'DD/MM/YYYY',
		useCurrent: false
	});
*/	
//	$.fn.tooltip.Constructor.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
//		container: 'body'
//	});
	
	$.fn.dataTable.defaults = $.extend({}, $.fn.dataTable.defaults, {
		"paging":   true,
        "ordering": true,
        "info":     true,
        "language": {
            "lengthMenu": "Mostrando _MENU_ registros por p&aacute;gina",
            "zeroRecords": "Ning&uacute;n dato encontrado",
            "info": "Mostrando _PAGE_ de _PAGES_",
            "infoEmpty": "0 elementos encontrados",
            "emptyTable":     "No hay datos disponibles en la tabla",
            "infoFiltered": "(filtrado de un total de _MAX_ elementos)",
            "search": "Buscar:",
            "decimal":        ",",
		    "thousands":      ".",
		    "loadingRecords": "Cargando...",
		    "processing":     "Procesando...",
            "paginate": {
                "first":      "<i class='fas fa-fast-backward'></i>",
                "last":       "<i class='fas fa-fast-forward'></i>",
                "next":       "<i class='fas fa-forward'></i>",
                "previous":   "<i class='fas fa-backward'></i>"
            }
        },
		dom: 'Bfrtip',
		buttons: [
            {
	            extend: 'copy',
	            text: 'Copiar'
	        }, 
            {
	            extend: 'excel',
	            text: 'Excel'
	        }, 
            {
	            extend: 'pdf',
	            text: 'Pdf'
	        }, 
            {
	            extend: 'print',
	            text: 'Imprimir'
	        }
        ]
	});
	
	var $loading = null;
	$.extend({
		/* */
		"loading": function(message) {
			if($loading == null) {
				$loading = $(HTML_LOADING({})).appendTo('body').modal().on('hidden.bs.modal', function() {
                	if($('.modal').hasClass('in')) {
                		$('body').addClass('modal-open');
                	}
                    $(this).remove();
                });
				$loading.find('.mensaje').html(message);
			} else {
				$loading.find('.mensaje').html(message);
			}
		},
		
		/* */
		"loaded": function() {
			$loading.modal('hide');
			$loading = null;
		},
		
		/* */
		"nvl": function(string, notnull) {
			if(!notnull)
				notnull = '';
			return string != null ? string : notnull;
		},
		
		/* */
		"obtener": function(url, data, callback) {
/*			Date.prototype.toJSON = function(){ console.log(this);return moment(this).format('DD/mm/yyyy'); }*/
		    $.ajax({
		    	type : "POST",
				contentType : "application/json",
				url : url,//"${home}search/api/getSearchResult",
				data : JSON.stringify(data),
				dataType : 'json',
				timeout : 100000,
		        success: callback,
		        error: function(e) {
		        	console.log("ERROR: ", e);
//		        	var html = Handlebars.compile(
//		        			' <div class="row"> ' +
//		        			' 	<div class="col-xs-3 text-center"> ' +
//		        			'       <span class="fa fa-exclamation-circle text-danger fa-5x"></span> ' +
//		        			' 	</div> ' +
//		        			' 	<div class="col-xs-8 text-justify"> ' +
//		        			' 		<p>Se ha producido un error en una de las consultas realizadas. La aplicaci�n podr�a no ' +
//		        			' 		funcionar correctamente. El mensaje de error ha sido el siguiente:</p> ' +
//		        			' 		<ul> ' +
//		        			' 		{{#each mensajes}} ' +
//		        			' 			<li>{{mensaje}}</li> ' +
//		        			' 		{{/each}} ' +
//		        			' 		</ul> ' +
//		        			' 		<p>Por favor, contacte con el soporte t�cnico.</p> ' +
//		        			' 	</div> ' +
//		        			' </div> '	
//		        	);
		        	bootbox.alert($.toHtml("ERROR: ", e));
		        },
		       done: function(e){
		    	   console.log("DONE");
		       }
		    });
		},
		
		"enviarForm": function(url, model, datos){
			var form = $('<form/>').attr({
				action: url,
				modelAttribute: model,
				method: "post",
			});
			
			for(dato in datos){
				$('<input type="hidden"/>').attr("name", dato).val(datos[dato]).appendTo(form);
			}
			form.appendTo('body').submit();
		},
		
		/* */
		"enviarFormAjax": function(url, datos, callback, msgLocation) {
    		var $form = $('<form/>').attr({
    			action: url,
    			method: 'post'
    		});
    		
    		//$('<input type="hidden"/>').attr("name", "accion").val(accion).appendTo($form);
    		
    		for(prop in datos) {
    			$('<input type="hidden"/>').attr("name", prop).val(datos[prop]).appendTo($form);
    		}
    		
    		$form.appendTo('body').enviar(url, callback, msgLocation);
    	},
		
		/* */
		"toHtml": function(i) {
			return i[0].outerHTML;
		},
		
		/* */
		"createButton": function(options) {
			var settings = $.extend({}, {
				libreria: 'fa', /* librer�a de iconos. Puede ser 'glyphicon' o 'fa' */
				prefijo: 'fa', /* prefijo de los nombres de iconos que usa la librer�a (por defecto igual que la libreria) */
				icono: '', /* nombre icono tras el nombre de la librer�a */
				texto: '',
				clases: '',
				title: '',
				size: '', /* xs, sm, lg */
				tipo: 'link', /* default, primary, link */
				color: '',
				row: true, /*indica que es un icono de fila, sin texto */
				link: undefined,
				number: undefined,
				data: {},
				click: function() {}
			}, options);
			
			var $span = $('<span/>');
			
			if(options.libreria && !options.prefijo)
				settings.prefijo = settings.libreria;
			
			if(settings.icono) 
				$span.addClass(settings.libreria+" "+settings.prefijo+"-"+settings.icono);
			
			if(settings.number != undefined) {
				$span.addClass('ise-with-number');
				$('<span>').addClass('ise-number').text(settings.number).appendTo($span);
			}
			
			if(settings.color)
				$span.css("color", settings.color);
			
			var $button = settings.link ? $('<a>').attr({href: settings.link, target: '_blank'}) : $('<button type="button"/>');
			
			if(settings.icono)
				$button.data('loadingText', '<span class="fa fa-refresh fa-spin"></span>')
			
			var click = settings.link ? function(e) { e.stopPropagation(); } : settings.click;
			
			$button
				.addClass("btn btn-"+settings.tipo)
				.append($span);
			
			if(settings.icono && !settings.loading)
				$button.data('loadingText', '<span class="fa fa-refresh fa-spin"></span>');
			else if(settings.loading)
				$button.data('loadingText', settings.loading);
			
			if(settings.row)
				$button.addClass('btn-row');
			
			if(settings.size)
				$button.addClass("btn-"+settings.size);
			
			if(settings.title){
				$button.attr('title', settings.title).tooltip()
					   .attr('title', settings.title).tooltip();
			}
			
			if(settings.texto) {
				$span.html($('<span>').addClass('texto').html(settings.texto));
				$button.css('margin', '0px 1px 0px 1px');
			}
			
			if(settings.data) {
				_.each(settings.data, function(value, key) {
					$button.attr("data-"+key, value).tooltip();
				});
			}
			
			$button.addClass(settings.clases);
			
			return $button;
		},
		
	});
	
	$.fn.extend({
		"esperarAjax": function(cb) {
			return $(this).each(function() {
				if($.active > 0) {
					$(this).one('ajax-wait.eGestion', cb);
				} else {
					cb.call(this);
				}
			});
		},
		
		"mostrar": function() {
        	return this.each(function() {
        		var $modal = $(this);
        		if($modal.is('.modal')) {
        			$(document).esperarAjax(function() {
        				$modal.modal('show');
        			});
        		}
        	});
        },
        
        /* */
		"reloadTable": function(data) {
    		var table = $(this);
    		table.dataTable().fnClearTable();
			if(data.length > 0){
				table.dataTable().fnAddData(data);
			}
    	},
    	
    	/* */
		"addBoton": function(options) {
			return $(this).each(function() {
				var settings = $.extend({}, {
					control: undefined,
					ocultar: true
				}, options);
				if(settings.control != 'N' || !settings.ocultar)
				{
					var button = $.createButton(options).appendTo(this);
					if(settings.control) {
						button.controlBoton(settings.control);
					}
				}
			});
		},
        
        "format": function() {
    		return this.each(function() {
    			var valor = $(this).is(':input') ? $(this).val() : $(this).text();
    	    	if(valor)
    	    	{
    	    		if($(this).hasClass('fecha_corta')) {
    		            valor = moment(valor).format('DD/MM/YYYY');
    		        } else if($(this).hasClass('fecha_larga')) {
    		            valor = valor.substring(0,19);
    		        } else if($(this).hasClass('hora_larga')) {
    		        	valor = valor.substring(11,19);
    		        } else if($(this).hasClass('hora_corta')) {
    		            valor = valor.substring(11,16);
    		        } else if($(this).hasClass('porcentaje')) {
    		            valor = valor + '%';
    		        }else if($(this).hasClass('sino')) {
    		        	valor = valor == 'S' ? 'S�' : 'No';
	    	    	}else if($(this).hasClass('truefalse')) {
			        	valor = valor == 'true' ? 'S�' : 'No';
			        }else if($(this).hasClass('importe')) {
    		        	if(valor==0 || (valor!=null && valor!='')) {
							var $an = $('<span>').autoNumeric({aSep: '.', aDec: ',', aSign: '', pSign: 's', vMin: '-999999999.99'});
							$an.autoNumeric('set', valor);
							valor = $an.text();
							$an.remove();
						} else {
							valor = '';
						}
    		        }
    	    		
    	    		if($(this).hasClass('autonumeric')) {
    	    			$(this).autoNumeric("set", valor);
    	    		} else {
    	    			$(this).is(':input') ? $(this).val(valor) : $(this).text(valor);
    	    		}
    	    	}
    		});
        },
        
        /* */
        "controlBoton": function(value) {
        	return $(this).each(function() {
        		if($(this).is('button') || $(this).is('a.btn')) {
        			var $button = $(this);
        			
        			//quito tooltip-wrapper si lo tiene
    				if($button.parent().is('.tooltip-wrapper'))
    					$button.unwrap();
    				
    				//habilitado solo si control='S'
    				$button.prop('disabled', value!='S').toggleClass('disabled', value!='S');    					
    				
    				//si puede ser ocultado
    				if($button.is('.btn-ocultar')) {
    					if(value=='N') {
    						$button.hide(); //lo oculto solo si es control='N'
    					} else {
    						$button.show(); //en otro caso lo muestro
    					}
    				}
    				
    				//si el control no es 'N' ni 'S'
    				if(value != 'N' && value != 'S') {
    					//preparo tooltip
    					var wrapper = $('<div/>').addClass('tooltip-wrapper').attr({
    						'data-container': 'body',
    						'data-html': 'true',
    						'data-placement': $button.data('placement') || 'top',
    						'title': value,
    					});
    					
    					//si el bot�n tenia btn-block, pasar la clase al tooltip-wrapper
    					if($button.is('.btn-block')) {
    						wrapper.addClass('btn-block');
    					}
    					
    					$button.wrap(wrapper).parent().tooltip(); //lo pongo y lo activo
    					wrapper.remove(); //$.wrap crea un clon, por lo que elimino el original para ahorrar memoria
    				}
        		}
        	});
        },
        
        "cargarDatos": function(options) {
    		return this.each(function() {
    			var settings = $.extend(true, {}, {
    				datos: {},
    				end: ''
    			}, options);
    			for(prop in settings.datos) {
    				var value = $.nvl(settings.datos[prop]);
    				if (value instanceof Object){
    					for(res in value) {
    						$(this).procesaDato(value[res], res, prop+'_');
    					}
    				} else {
    					$(this).procesaDato(value, prop, '');
    				}
    			}
    			for(prop in settings.datos) {
    				$('.'+prop+':input', this).change();
    			}
    			
    			$('[data-href]', this).each(function() {
    				var $elem = $(this);
    				var template = Handlebars.compile($elem.data('href'));
    				$elem.attr('href', template(settings.datos));
    			});
    			
    			if(settings.end && typeof(settings.end)==='function')
    				settings.end.call(this);
    		});
    	},
    	
    	"procesaDato": function(value, prop, extra) {
			$('.'+extra+prop+':input', this).each(function() {
				if($(this).is('select.selectpicker')) {
					$(this).selectpicker('val', value);
				} else if($(this).is('input[type="checkbox"]')) {
					$(this).prop('checked', value==$(this).val());
				} else if($(this).is('input[type="radio"]')) {
					if(value==$(this).val())
						$(this).prop('checked', value==$(this).val()).trigger('click');
				} else if($(this).is('select')) {
					$(this).val(value).data('loaded', value);
				} else {
					$(this).val(value).format();
				}
			});
			$('span.'+prop, this).add('p.'+prop, this).each(function() {
				$(this).html(value).format();
			});
			$('h4.'+prop, this).each(function() {
				$(this).html(value).format();
			});
			
//			$('button.'+prop, this).controlBoton(value);
			
			$('*[data-mostrar="'+prop+'"]', this).each(function() {
				if(value=='S')
					$(this).show();
				else
					$(this).hide();
			});
			$('*[data-ocultar="'+prop+'"]', this).each(function() {
				if(value=='S')
					$(this).hide();
				else
					$(this).show();
			});
			$('*[data-edicion="'+prop+'"]', this).prop('disabled', value=='N');
    	},
		
		/* */
		"limpiar": function() {
    		return $(this).each(function() {
    			$(':input[type!=submit][type!=checkbox][type!=radio][type!=file][name!=accion]:not(.no-limpiar)', this).val('');
   				$('input[type=checkbox]:not(.no-limpiar), input[type=radio]:not(.no-limpiar)', this).prop('checked', false);
   				
   				if($.fn.selectpicker) {
   					$('select.selectpicker', this).selectpicker('val', '');
   				}
   				
   				$('select', this).data('loaded', '');
   				
   				$('input[type=file]:not(.no-limpiar)', this).each(function() {
   					$(this).wrap('<form>').closest('form').get(0).reset();
   					$(this).unwrap();
   				});
   				
   				$('form', this).add(this).each(function(){
   					var form = $(this);
   					if(form.hasClass('validation')) {
   						var fv = form.validate();
   						fv.resetForm();
   					}
   				});
   				
    		});
    	},
    	
    	"enviar": function(url, callback) {
    		return this.each(function() {
    			var element = $(this);
    			var button = element.find('.btn-fv-submit');
    			var $form = element.is('form') ? element : $('form', element);
    			if(button.length == 0)
    				button = element.closest('.modal').find('.btn-fv-submit, .btn-bv-submit');
    			
    			var fv;
    			if($form.hasClass('validation')) {
					fv = $form.validate();
//					console.log($form.valid());
				}
    			
    			$(document).esperarAjax(function() {
	    			if(!$form.hasClass('validation') || $form.valid()) {
	    				
	    				if($form.length == 0) {
	    					$form = $('<form enctype="multipart/form-data">');
	    					element.wrapInner($form);
	    					$form = $('form', element);
	    				}
	    				
	    				if(button) 
	    					$.loading('Enviando...');
	    				
	    				var $disabledControls = $(':input:disabled', $form).prop('disabled', false);
	    				
	    				var params = $form.formData();
	    				params = $.extend({}, params, {'iehack': '&#9760;'});
						if(1==1){
						 $.ajax({
								type : "POST",
								contentType : "application/json",
								url : url,
								data : JSON.stringify(params),
								dataType : 'json',
								timeout : 100000,
						        success: function(data) {
						        	$disabledControls.prop('disabled', true);
						        	
						        	if(button)
						        		button.button('reset');
						        	
						        	//Si la petici�n es correcta, guarda en elemento una bandera que se comprobar� luego con la funci�n "checkUpdated"
						        	if((data.ok && data.ok==='S') || (data.correcto && data.correcto==='S')) {
						        		element.data('updated', 'S');
						        	}
						        	
									if(callback && typeof callback==='function') {
										callback.call(element, data);
									}
									
									if(data.mensajes) {
										$.each(data.mensajes, function(index, elem) {
											var settings = {
												type: elem.tipo
											};
											
											$.notify({
												message: elem.mensaje
											}, settings);
										});
									}
									
									$.loaded();
						        },
						        error: function(e) {
						        	$.loaded();
						        	console.log("ERROR: ", e);
						        	if(button)
						        		button.button('reset');
									
									$.notify({
										message: e
									}, {
										type: 'danger',
										delay: 0
									});
									
//									bootbox.alert(html("ERROR: ", e));
						        },
						       done: function(e){
						    	   $.loaded();
						    	   console.log("DONE");
						       }
						    });
						}
	    			}
    			});
    		});
    	},
    	
        /* */
        "formData": function() {
    		var $form = $(this);
    		if($form.is('form')) {
				return _.reduce($form.serializeArray(), function(memo, item) {
					var name = item.name;
					if(name.includes(".") && item.value != ""){
						var anterior = memo;
						var atributos = _.split(item.name, '.' );
						var contador = 0;
						$.each(atributos, function( index, value ) {
							anterior[value] = {};
							if(contador >= atributos.length-1){
								anterior[value] = item.value;
							}else{
								anterior = anterior[value];
							}
							contador++;
                        });
					}
					else {
						memo[name] = item.value;
					}
					return memo;
				}, {});
    		} else {
    			return {};
    		}
        },
        
    	/* */
		"prepararDOM": function() {
			return $(this).each(function() {
				$('form', this).add(this).each(function(){
   					var form = $(this);
   					if(form.hasClass('validation')) {
   						var fv = form.validate();
   						fv.resetForm();
   					}
   				});
		    	
				// configuramos el textcounter para todos los textarea con maxlength 
//				$('textarea[maxlength]', this).maxlength({
//					alwaysShow: true
//				});
				
				// prepara un input para formatear n�meros autom�ticamente
				$('input.autonumeric', this).each(function() {
					return $(this).each(function() {
						$(this).autoNumeric({aSep: '.', aDec: ',', vMin: '-999999999.99'}).on('change blur', function(){
							var fvElement = $(this).closest(".validation");
							if(fvElement.length > 0) {
								var fv = fvElement.data('formValidation');
								if(fv.getFieldElements($(this).attr('name')) != null) //TODO no revalidar si no est� en el formulario
									fv.revalidateField($(this));
							}
						});
					});
				});
				
				// un boton con data-form-submit comprueba automaticamente si el formulario es valido
				// antes de enviarlo, poniendole al boton el texto de espera
				$('button[data-submit]', this).each(function() {
					var $this = $(this); 
					var form = $this.data('submit') ? $($this.data('submit')) : $(this).closest('form'); 
					$(this).click(function(e) {
						e.stopPropagation(); e.preventDefault();
						if(form.hasClass('validation')) {
							var fv = form.validate();
							fv.resetForm();
							if(fv.form()) {
								$this.button('loading');
								form.submit();
							}
						} else {
							$this.button('loading');
							form.submit();
						}
					});
				});
				
				// un boton con data-form-limpiar limpiar el formulario asociado, ya sea indicado por el atributo
				// data-form-limpiar, o bien el formulario en donde est� incluido el bot�n.
				$('button[data-limpiar]', this).each(function() {
					var $this = $(this); 
					var form = $this.data('limpiar') ? $($this.data('limpiar')) : $(this).closest('form'); 
					$(this).click(function(e) {
						e.stopPropagation(); e.preventDefault();
						form.limpiar();
					});
				});
			});
		}
	});
	
	$(document).ajaxStop(function() {
		$('*').trigger('ajax-wait.eGestion');
	});
	
	$(document).ready(function() {
		
		$('body').prepararDOM().on('hidden.bs.modal', '.modal', function() {
			$('form', this).each(function(){
				var form = $(this);
				if(form.hasClass('validation')) {
					var fv = form.validate();
					fv.resetForm();
				}
			});
			
			if($('.modal').hasClass('in')) {
	        	$('body').addClass('modal-open');
	        }
		});
		
		var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		
		
//		$.fn.datepicker.defaults.format = "dd/mm/yyyy";
//		$('.datepicker').datepicker({
//            todayBtn: "linked",
//            format: 'dd/mm/yyyy',
//            language: 'es',
//            container: container,
//            todayHighlight: true,
//            autoclose: true
//		});
		
//		$('.datepicker').datepicker({
//            format: "dd/mm/yyyy",
//            todayBtn: "linked",
//            container: "body",
//            language: "es",
//            autoclose: true,
//            todayHighlight: true
//        });
		
		$('table.extendida').DataTable({
			"footerCallback": function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	            var column = $('table.extendida .sumatorio').data("column")
	            
	            var pattern = ' ';
	            var replacement = '';
	            
	            // Remove the formatting to get integer data for summation
	            var intVal = function ( i ) {
	            	return typeof i === 'string' ?
	            			parseFloat(i.substring(0,i.length-2).replace(',', '.')) :
		                    typeof i === 'number' ?
		                        i : 0;
	            };
	            
	            // Total over all pages
	            total = api
	                .column(column)
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	            
	            // Total over this page
	            pageTotal = api
	                .column(column, { page: 'current'} )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b) ;
	                }, 0 );
	 
	            // Update footer
	            $(api.column(2).footer() ).html(
	                (pageTotal +'&euro; ('+ total +'&euro; total)').replace('.', ',')
	            );
	        }
		});
		
		$.validator.addMethod("fechaValida", function(value, element) {
        	return this.optional(element) || moment(value,"DD/MM/YYYY").isValid();
    	}, "Por favor introduce una fecha v&aacute;lida con el formato DD/MM/YYYY");
		
		$.extend($.validator.messages, {
		  required: "Este campo es obligatorio.",
		  remote: "Por favor, rellena este campo.",
		  email: "Por favor, escribe una direcci&oacute;n de correo v&aacute;lida",
		  url: "Por favor, escribe una URL v&aacute;lida.",
		  date: "Por favor, escribe una fecha v&aacute;lida.",
		  dateISO: "Por favor, escribe una fecha (ISO) v&aacute;lida.",
		  number: "Por favor, escribe un n&uacute;mero entero v&aacute;lido.",
		  digits: "Por favor, escribe s&oacute;lo d&iacute;gitos.",
		  creditcard: "Por favor, escribe un n&uacute;mero de tarjeta v&aacute;lido.",
		  equalTo: "Por favor, escribe el mismo valor de nuevo.",
		  accept: "Por favor, escribe un valor con una extensi&oacute;n aceptada.",
		  maxlength: jQuery.validator.format("Por favor, no escribas m&aacute;s de {0} caracteres."),
		  minlength: jQuery.validator.format("Por favor, no escribas menos de {0} caracteres."),
		  rangelength: jQuery.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		  range: jQuery.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		  max: jQuery.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		  min: jQuery.validator.format("Por favor, escribe un valor mayor o igual a {0}.")
		});
    });
	
})(jQuery);