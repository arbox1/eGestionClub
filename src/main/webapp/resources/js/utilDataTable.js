(function() {
	$.fn.dataTable.render.importe = function() {
	    return function (data, type, row) {
	        if ( type === 'display' ) {
	            var $an = $('<span>').autoNumeric({aSep: '.', aDec: ',', aSign: '', pSign: 's', vMin: '-999999999.99'});
				$an.autoNumeric('set', data);
				var valor = $an.text() + "&euro;";
				$an.remove();
				return valor;
	        }
	        return '';
	    };
	};
})(jQuery);