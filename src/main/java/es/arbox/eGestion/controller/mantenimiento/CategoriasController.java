package es.arbox.eGestion.controller.mantenimiento;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.socios.Categoria;

@Controller
@RequestMapping("/mantenimiento/categorias")
public class CategoriasController extends MantenimientoController<Categoria>{

	@Override
	public String getReturn() {
		return "/mantenimiento/categorias";
	}

	@Override
	public Categoria getDato() {
		return new Categoria();
	}

	@Override
	public Map<String, Object> addModel() {
		return null;
	}
}