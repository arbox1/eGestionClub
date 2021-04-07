package es.arbox.eGestion.controller.socios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.entity.socios.Categoria;
import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.enums.TiposMensaje;
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
	public String listaSocios(Model model, @ModelAttribute("buscador") SociosCurso socio) {
		model.addAttribute("totales", 0);
		model.addAttribute("sociosCurso", new ArrayList<SociosCurso>());
		model.addAttribute("escuelas", cuotaService.obtenerTodosOrden(Escuela.class, "descripcion"));
		model.addAttribute("categorias", cuotaService.obtenerTodosOrden(Categoria.class, "id"));
		model.addAttribute("cursos", cuotaService.obtenerTodosOrden(Curso.class, "id"));
		model.addAttribute("meses", sociosCursoService.getMeses());
		model.addAttribute("buscador", socio == null ? new SociosCurso() : socio);
		return "/socios/cuotas";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") SociosCurso socio, RedirectAttributes redirectAttrs) {
		
		Double totales = new Double(0);
		List<SociosCurso> sociosCurso = sociosCursoService.obtenerSociosFiltro(
				socio.getCurso().getId()/*Curso*/,
				socio.getEscuela().getId()/*Escuela*/,
				socio.getCategoria().getId()/*Categoría*/);
		
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
		model.addAttribute("escuelas", cuotaService.obtenerTodosOrden(Escuela.class, "descripcion"));
		model.addAttribute("categorias", cuotaService.obtenerTodosOrden(Categoria.class, "id"));
		model.addAttribute("cursos", cuotaService.obtenerTodosOrden(Curso.class, "id"));
		model.addAttribute("buscador", socio);
		
		return "/socios/cuotas";
    }
	
	@ResponseBody
	@PostMapping(value = "/guardarCuota", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String guardarCuota(@RequestBody Cuota cuota, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = cuota.getId() != null ? "actualizada" : "realizada";
		
		cuotaService.guardar(cuota);
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Cuota %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return cuotaService.serializa(result);
    }
}
