package es.arbox.eGestion.controller.mantenimiento;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.economica.Tarifa;

@Controller
@RequestMapping("/mantenimiento/tarifas")
public class TarifasController extends MantenimientoController<Tarifa>{

	@Override
	public String getReturn() {
		return "/mantenimiento/tarifas";
	}

	@Override
	public Tarifa getDato() {
		return new Tarifa();
	}
}