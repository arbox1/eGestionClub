package es.arbox.eGestion.controller.socios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.service.socios.CuotaService;
import es.arbox.eGestion.service.socios.SociosCursoService;

@Controller
@RequestMapping("/socios/cuotas")
public class CuotasController {
	
	@Autowired
	private SociosCursoService sociosCursoService;
	
	@Autowired
	private CuotaService cuotaService;
	
	@GetMapping("/")
	public String listaSocios(Model model) {
		Double totales = new Double(0);
		List<SociosCurso> sociosCurso = sociosCursoService.obtenerSociosFiltro(
				2/*Curso*/,
				1/*Escuela*/,
				7/*Categoría*/);
		
		for(SociosCurso socioCurso : sociosCurso) {
			Map<Integer, Cuota> mCuotas = new HashMap<Integer, Cuota>();
			for(Cuota cuota : cuotaService.getCuotas(socioCurso.getId())) {
				mCuotas.put(cuota.getMes().getId(), cuota);
				totales+= cuota.getImporte() != null ? cuota.getImporte() : 0;
			}
			socioCurso.setCuotas(mCuotas);
		}
		
		model.addAttribute("totales", totales);
		model.addAttribute("sociosCurso", sociosCurso);
		
		model.addAttribute("meses", sociosCursoService.getMeses());
		return "/socios/cuotas";
	}
}
