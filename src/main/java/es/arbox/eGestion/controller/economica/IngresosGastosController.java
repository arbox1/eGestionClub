package es.arbox.eGestion.controller.economica;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.economica.DocumentoIngresoGasto;
import es.arbox.eGestion.entity.economica.IngresosGastos;
import es.arbox.eGestion.entity.economica.SubtiposImporte;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.economica.DocumentoIngresoGastoService;
import es.arbox.eGestion.service.economica.IngresosGastosService;

@Controller
@RequestMapping("/economica/ingresosGastos")
public class IngresosGastosController extends BaseController {
	
	@Autowired
	IngresosGastosService ingresosGastosService;
	
	@Autowired
	DocumentoIngresoGastoService documentoIngresoGastoService;
	
	@GetMapping("/")
	public String listaSocios(Model model, @ModelAttribute("buscador") IngresosGastos ingresoGasto) {
		model.addAttribute("ingresos", ingresosGastosService.obtenerTodosOrden(IngresosGastos.class, " fecha desc, descripcion "));
		model.addAttribute("tipos", ingresosGastosService.obtenerTodosOrden(SubtiposImporte.class, " tipoImporte.descripcion, descripcion"));
		model.addAttribute("tiposDocumentos", documentoIngresoGastoService.getTipoDocumento(FamiliasDocumento.INGRESO_GASTO));
		model.addAttribute("nuevo", new IngresosGastos());
		model.addAttribute("buscador", ingresoGasto == null ? new IngresosGastos() : ingresoGasto);
		return "/economica/ingresosGastos";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") IngresosGastos ingresoGasto, RedirectAttributes redirectAttrs) {
		
		model.addAttribute("ingresos", ingresosGastosService.getBusqueda(ingresoGasto));
		model.addAttribute("tipos", ingresosGastosService.obtenerTodosOrden(SubtiposImporte.class, " tipoImporte.descripcion, descripcion"));
		model.addAttribute("tiposDocumentos", documentoIngresoGastoService.getTipoDocumento(FamiliasDocumento.INGRESO_GASTO));
		model.addAttribute("nuevo", new IngresosGastos());
		model.addAttribute("buscador", ingresoGasto);
		
		return "/economica/ingresosGastos";
    }
	
	@PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevo") IngresosGastos ingresoGasto, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = ingresoGasto.getId() != null ? "actualizado" : "creado";
		if (ingresoGasto.getFecha() == null)	
			ingresoGasto.setFecha(new Date());
		ingresosGastosService.guardar(ingresoGasto, getUsuarioLogado());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Ingreso / Gasto %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
        return "redirect:/economica/ingresosGastos/";
    }
	
	@PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("nuevo") IngresosGastos ingresoGasto, RedirectAttributes redirectAttrs) {
		ingresosGastosService.eliminar(ingresoGasto.getClass(), ingresoGasto.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Ingreso / Gasto eliminado correctamente.");
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return "redirect:/economica/ingresosGastos/";
    }
	
	@ResponseBody
	@PostMapping(value = "/cargar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String cargar(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		IngresosGastos ingresoGasto = ingresosGastosService.obtenerPorId(IngresosGastos.class, valores.getId());
		
		result.setResultado("ingresoGasto", ingresoGasto.getMapa());
		
		return ingresosGastosService.serializa(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/documentos", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String documentos(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<DocumentoIngresoGasto> documentoIngresoGasto = documentoIngresoGastoService.getDocumentos(valores.getId());
		
		result.setResultado("documento", DocumentoIngresoGasto.getListaMapa(documentoIngresoGasto));
		
		return documentoIngresoGastoService.serializa(result);
	}
	
	@PostMapping(value = "/guardarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarDocumento(@ModelAttribute DocumentoIngresoGasto documentoIngresoGasto, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = documentoIngresoGasto.getId() != null ? "actualizado" : "realizado";
		
		documentoIngresoGastoService.guardar(documentoIngresoGasto.getDocumento(), getUsuarioLogado());
		documentoIngresoGastoService.guardar(documentoIngresoGasto);
		
		result.setResultado("id", documentoIngresoGasto.getIngresoGasto().getId());
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Documento %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax eliminarDocumento(@ModelAttribute DocumentoIngresoGasto documentoIngresoGasto, RedirectAttributes redirectAttrs) throws JsonProcessingException {
		RespuestaAjax result = new RespuestaAjax();
		
		Integer idDocumento = documentoIngresoGasto.getDocumento().getId();

		documentoIngresoGastoService.eliminar(DocumentoIngresoGasto.class, documentoIngresoGasto.getId());
		documentoIngresoGastoService.eliminar(Documento.class, idDocumento);
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Documento eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
}
