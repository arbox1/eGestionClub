(function($) {
	$.fn.select = function(method) {

		var defaults = {
			accion: '',
			datos: {},
			value: 'id',
			label: 'descripcion',
			loading: 'Cargando...',
			blank: '',
			forceSingleSelection: false,
		};
		
		var element = $(this);
		
		var methods = {
			"init": function(options) {
				return this.each(function() {
					var settings = $.extend(true, {}, defaults, options);
					element.data('select_settings', settings);
				});
			},
			
			"cargar": function(datos, selected, callback) {
				return this.each(function() {
					var settings = element.data('select_settings');
					
					if(settings.loading) {
						element.html('<option value="">'+settings.loading+'</option>');
					}
					
					$.obtener(settings.accion, $.extend({}, settings.datos, datos), function(data){
						selected = selected ? selected : element.data('loaded');
						
						if(!settings.blank || (settings.forceSingleSelection && data.length==1)) {
							element.html('');
						} else {
							element.html('<option value="">'+settings.blank+'</option>');
						}
						if(data) {
							$.each(data.resultados.datos, function(index, elem) {
								var label = (typeof settings.label==='function') ? settings.label(elem, index) : elem[settings.label];
								var value = (typeof settings.value==='function') ? settings.value(elem, index) : elem[settings.value];
								var $option = $('<option>').attr('value', value).text(label).appendTo(element);
							});
							
							if(selected) {
								element.val(selected).change();
							}
						}
						
						if(callback && typeof callback==='function') {
							callback.call(element);
						}
						
						if(settings.end && typeof settings.end==='function') {
							settings.end.call(element);
						}
						
					});
				});
			}
		};
		
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error( 'Method "' +  method + '" does not exist in pluginName plugin!');
		}	
	};
})(jQuery);