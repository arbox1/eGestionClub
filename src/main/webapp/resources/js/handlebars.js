(function() {
	var traducirUrl = function(name) {
		return context+'resources/plantillas/'+name.replace(/\./g, '/').toLowerCase()+'.hbs?t='+Date.now();
	};
	
	Handlebars.cargarPlantilla = function(plantilla) {
		var info = {
			loaded: false
		};
		console.log(plantilla);
		$.get(traducirUrl(plantilla), function(data) {
			//$.plantillas[nombre] = Handlebars.compile(data);
			info.template = Handlebars.compile(data);
			info.loaded = true;
			console.log("hecho");
		});
		
		return function() {
			while(!info.loaded);
			return info.template.apply(null, arguments);
		};
	}
	
	Handlebars.cargarPromesaPlantilla = function(plantilla) {
		var dfd = $.Deferred();
		$.get(traducirUrl(plantilla), function(data) {
			//$.plantillas[nombre] = Handlebars.compile(data);
			dfd.resolve(Handlebars.compile(data));
		});
		
		return dfd.promise();
	}
	
	Handlebars.cargarPlantillas = function(plantillas, prefijo) {
		if(_.isArray(plantillas)) {
			plantillas = _(plantillas).map(function(plantilla) {
				return prefijo ? prefijo+plantilla : plantilla;
			}).reduce(function(obj, plantilla) {
				var nombre = _.camelCase(_.last(plantilla.split('.')));
				obj[nombre] = plantilla;
				return obj;
			}, {});
		}
		
		var dfd = $.Deferred();
		
		async.eachOf(plantillas, function(plantilla, nombre, done) {
			Handlebars.cargarPromesaPlantilla(plantilla, nombre).done(function(plantillaCompilada) {
				plantillas[nombre] = plantillaCompilada;
				done();
			});
		}, function(err) {
			dfd.resolve(plantillas);
		});
		
		return dfd.promise();
	}
	
	Handlebars.cargarParcial = function(nombre, plantilla) {
		$.get(traducirUrl(plantilla), function(data) {
			Handlebars.registerPartial(nombre, data);
		});
	};
	
	Handlebars.registerHelper('context', function () {
		return new Handlebars.SafeString(context);
	});
	
	Handlebars.registerHelper('check', function (value, options) {
		if(value=='S') {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	});
	
	Handlebars.registerHelper('compare', function (lvalue, operator, rvalue, options) {
	
	    var operators, result;
	    
	    if (arguments.length < 3) {
	        throw new Error("Handlerbars Helper 'compare' needs 2 parameters");
	    }
	    
	    if (options === undefined) {
	        options = rvalue;
	        rvalue = operator;
	        operator = "===";
	    }
	    
	    operators = {
	        '==': function (l, r) { return l == r; },
	        '===': function (l, r) { return l === r; },
	        '!=': function (l, r) { return l != r; },
	        '!==': function (l, r) { return l !== r; },
	        '<': function (l, r) { return l < r; },
	        '>': function (l, r) { return l > r; },
	        '<=': function (l, r) { return l <= r; },
	        '>=': function (l, r) { return l >= r; },
	        'typeof': function (l, r) { return typeof l == r; }
	    };
	    
	    if (!operators[operator]) {
	        throw new Error("Handlerbars Helper 'compare' doesn't know the operator " + operator);
	    }
	    
	    result = operators[operator](lvalue, rvalue);
	    
	    if (result) {
	        return options.fn(this);
	    } else {
	        return options.inverse(this);
	    }
	
	});
	
	Handlebars.registerHelper("switch", function(value, options) {
	    this._switch_value_ = value;
	    this._shown = false;
	    var html = options.fn(this); // Process the body of the switch block
	    delete this._switch_value_;
	    delete this._shown;
	    return html;
	});
	
	Handlebars.registerHelper("case", function() {
	    // Convert "arguments" to a real array - stackoverflow.com/a/4775938
	    var args = Array.prototype.slice.call(arguments);
	
	    var options    = args.pop();
	    var caseValues = args;
	
	    if (caseValues.indexOf(this._switch_value_) === -1) {
	        return '';
	    } else {
	    	this._shown = true;
	        return options.fn(this);
	    }
	});
	
	Handlebars.registerHelper("others", function(options) {
		if(!this._shown)
			return options.fn(this);
		else
			return '';
	});
})();