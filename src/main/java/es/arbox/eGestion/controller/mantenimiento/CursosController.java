package es.arbox.eGestion.controller.mantenimiento;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.socios.Curso;

@Controller
@RequestMapping("/mantenimiento/cursos")
public class CursosController extends MantenimientoController<Curso>{

	@Override
	public String getReturn() {
		return "/mantenimiento/cursos";
	}

	@Override
	public Curso getDato() {
		return new Curso();
	}
}