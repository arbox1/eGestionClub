package es.arbox.eGestion.controller.mantenimiento;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.actividades.TiposActividad;

@Controller
@RequestMapping("/mantenimiento/tiposActividad")
public class TiposActividadController extends MantenimientoController<TiposActividad>{

	@Override
	public String getReturn() {
		return "/mantenimiento/tiposActividad";
	}

	@Override
	public TiposActividad getDato() {
		return new TiposActividad();
	}

	@Override
	public Map<String, Object> addModel() {
		return null;
	}
}