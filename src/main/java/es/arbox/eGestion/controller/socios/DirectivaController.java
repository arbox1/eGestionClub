package es.arbox.eGestion.controller.socios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.controller.mantenimiento.MantenimientoController;
import es.arbox.eGestion.entity.socios.Directiva;

@Controller
@RequestMapping("/socios/directiva")
public class DirectivaController extends MantenimientoController<Directiva>{

	@Override
	public String getReturn() {
		return "/socios/directiva";
	}

	@Override
	public Directiva getDato() {
		return new Directiva();
	}
}
