package es.arbox.eGestion.controller.mantenimiento;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.socios.Escuela;

@Controller
@RequestMapping("/mantenimiento/escuelas")
public class EscuelasController extends MantenimientoController<Escuela>{

	@Override
	public String getReturn() {
		return "/mantenimiento/escuelas";
	}

	@Override
	public Escuela getDato() {
		return new Escuela();
	}

	@Override
	public Map<String, Object> addModel() {
		return null;
	}
}