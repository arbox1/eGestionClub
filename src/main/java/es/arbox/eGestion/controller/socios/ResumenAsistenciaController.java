package es.arbox.eGestion.controller.socios;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ResumenAsistenciaDTO;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.economica.Pago;
import es.arbox.eGestion.entity.economica.Tarifa;
import es.arbox.eGestion.entity.socios.AsistenciaMonitor;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.entity.socios.Escuela;
import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.socios.AsistenciaMonitorService;

@Controller
@RequestMapping("/socios/resumenAsistencia")
public class ResumenAsistenciaController extends BaseController{
	
	@Autowired
	AsistenciaMonitorService asistenciaMonitorService;
	
	private String MES[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	
	@GetMapping("/")
	public String inicio(Model model, @ModelAttribute("buscador") AsistenciaMonitor aistencia) {
		model.addAttribute("asistencia", new ArrayList<AsistenciaMonitor>());
		model.addAttribute("usuarios", asistenciaMonitorService.obtenerTodosOrden(Usuario.class, " apellido1, apellido2, nombre "));
		model.addAttribute("tarifas", asistenciaMonitorService.obtenerTodosFiltroOrden(Tarifa.class, " activo = 'S' ", "descripcion"));
		model.addAttribute("escuelas", asistenciaMonitorService.obtenerTodosOrden(Escuela.class, "descripcion"));
		model.addAttribute("cursos", asistenciaMonitorService.obtenerTodosOrden(Curso.class, "id desc"));
		model.addAttribute("buscador", aistencia == null ? new AsistenciaMonitor() : aistencia);
		return "/socios/resumenAsistencia";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") AsistenciaMonitor asistencia, RedirectAttributes redirectAttrs) {
		
		List<Meses> meses = asistenciaMonitorService.obtenerTodosOrden(Meses.class, "orden");
		model.addAttribute("usuarios", asistenciaMonitorService.obtenerTodosOrden(Usuario.class, " apellido1, apellido2, nombre "));
		model.addAttribute("tarifas", asistenciaMonitorService.obtenerTodosFiltroOrden(Tarifa.class, " activo = 'S' ", "descripcion"));
		model.addAttribute("escuelas", asistenciaMonitorService.obtenerTodosOrden(Escuela.class, "descripcion"));
		model.addAttribute("cursos", asistenciaMonitorService.obtenerTodosOrden(Curso.class, "id desc"));
		
		if(StringUtils.isNotBlank(asistencia.getObservaciones())) {
			String[] años = asistencia.getObservaciones().split("-");
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.valueOf(años[0]), 8, 1);
			asistencia.setFechaDesde(cal.getTime());
			cal.set(Integer.valueOf(años[1]), 7, 31);
			asistencia.setFechaHasta(cal.getTime());
		}
		
		List<ResumenAsistenciaDTO> resumen = asistenciaMonitorService.getResumenAsistencia(asistencia, MES);
		Double totales = new Double(0.0);
		for(ResumenAsistenciaDTO dato : resumen) {
			for(Meses mes : meses) {
				if(dato.asistencia(mes.getNumero()-1).getImporte() != null) {
					mes.setTotal(
							mes.getTotal() == null ?
								dato.asistencia(mes.getNumero()-1).getImporte()
								: mes.getTotal() + dato.asistencia(mes.getNumero()-1).getImporte());
					
					totales += dato.asistencia(mes.getNumero()-1).getImporte();
				}
			}
		}
		
		model.addAttribute("meses", meses);
		model.addAttribute("totales", totales);
		model.addAttribute("asistencias", resumen);
		
		return "/socios/resumenAsistencia";
    }

	
	@PostMapping(value = "/guardar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Pago pago, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = pago.getId() != null ? "actualizado" : "realizado";
		
		if(pago.getFecha() == null)
			pago.setFecha(new Date());
		asistenciaMonitorService.guardar(pago, getUsuarioLogado());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Pago %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/asistencias", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String asistencias(@RequestBody AsistenciaMonitor asistencia) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		Usuario usuario = new Usuario();
		usuario.setId(asistencia.getId());
		asistencia.setId(null);
		asistencia.setMonitor(usuario);
		
		List<AsistenciaMonitor> asistencias = asistenciaMonitorService.getAsistencias(asistencia);
		
		result.setResultado("asistencias", Participante.getListaMapa(asistencias));
		
		return asistenciaMonitorService.serializa(result);
	}
}
