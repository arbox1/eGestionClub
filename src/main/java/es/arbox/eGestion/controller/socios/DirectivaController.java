package es.arbox.eGestion.controller.socios;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.controller.mantenimiento.MantenimientoController;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.reservas.Sede;
import es.arbox.eGestion.entity.socios.Directiva;

@Controller
@RequestMapping("/socios/directiva")
public class DirectivaController extends MantenimientoController<Directiva>{
	
	@GetMapping("/")
	public String listaDatos(Model model) {
		model.addAttribute("valor", new ValoresDTO());
		model.addAttribute("dato", getDato());
		model.addAttribute("datos", menuService.obtenerTodos(getDato().getClass()));
		model.addAttribute("sedes", menuService.obtenerTodos(Sede.class));
		return getReturn();//"/mantenimiento/cursos";
	}

	@Override
	public String getReturn() {
		return "/socios/directiva";
	}

	@Override
	public Directiva getDato() {
		return new Directiva();
	}

	@Override
	public Map<String, Object> addModel() {
		return null;
	}
}
